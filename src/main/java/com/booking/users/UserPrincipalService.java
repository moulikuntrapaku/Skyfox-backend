package com.booking.users;

import com.booking.exceptions.NewAndOldPasswordMatchException;
import com.booking.exceptions.OldThreePasswordMatchException;
import com.booking.exceptions.OldPasswordIncorrectException;
import com.booking.exceptions.UserNameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPrincipalService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserPrincipalService(UserRepository userRepository, PasswordHistoryRepository passwordHistoryRepository) {
        this.userRepository = userRepository;
        this.passwordHistoryRepository = passwordHistoryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User savedUser = findUserByUsername(username);
        return new UserPrincipal(savedUser);
    }


    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void changePassword(UserDTO userDTO) throws OldPasswordIncorrectException, OldThreePasswordMatchException, UserNameNotFoundException, NewAndOldPasswordMatchException {
        User user = userRepository.findByUsername(userDTO.getUserName()).orElseThrow(() -> new UserNameNotFoundException("User not found"));

        if(!bCryptPasswordEncoder.matches(userDTO.getOldPassword(), user.getPassword())) throw new OldPasswordIncorrectException("Old password incorrect");
        if (userDTO.getOldPassword().equals(userDTO.getNewPassword())) throw new NewAndOldPasswordMatchException("Old password and New password can't be same");

        List<PasswordHistory> passwordHistories = passwordHistoryRepository.findTop2ByUserIdOrderByEntrytimeDesc(user.getId());
        for (PasswordHistory passwordHistory : passwordHistories){
            if (userDTO.getNewPassword().equals(passwordHistory.getPassword())) {
                throw new OldThreePasswordMatchException("Password should not match with last three passwords");
            }

        }
        userDTO.setNewPassword(bCryptPasswordEncoder.encode(userDTO.getNewPassword()));
        user=userDTO.mapUserDtoToUser(userDTO, user);
        user.getPasswordHistories().add(new PasswordHistory(user.getId(), userDTO.getOldPassword()));
        userRepository.save(user);
    }

}
