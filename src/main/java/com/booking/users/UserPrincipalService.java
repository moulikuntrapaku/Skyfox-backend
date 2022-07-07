package com.booking.users;

import com.booking.exceptions.OldThreePasswordMatchException;
import com.booking.exceptions.OldPasswordIncorrectException;
import com.booking.exceptions.UserNameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPrincipalService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;


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

    public void changePassword(UserDTO userDTO) throws OldPasswordIncorrectException,OldThreePasswordMatchException , UserNameNotFoundException {
        User user = userRepository.findByUsername(userDTO.getUserName()).orElseThrow(() -> new UserNameNotFoundException("User not found"));

        if (!userDTO.getOldPassword().equals(user.getPassword())) throw new OldPasswordIncorrectException("Old password is incorrect");

        List<PasswordHistory> passwordHistories = passwordHistoryRepository.findTop3ByUserIdOrderByEntrytimeDesc(user.getId());
        for (PasswordHistory passwordHistory : passwordHistories){
            System.out.println("Password"+userDTO.getNewPassword()+" "+passwordHistory.getPassword());
            if (userDTO.getNewPassword().equals(passwordHistory.getPassword())) {
                throw new OldThreePasswordMatchException("Password should not match with last three passwords");
            }

        }
        user=userDTO.mapUserDtoToUser(userDTO, user);
        userRepository.save(user);
        user.getPasswordHistories().add(new PasswordHistory(user.getId(), userDTO.getNewPassword()));
    }

    public List<PasswordHistory> findPassHisById(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return passwordHistoryRepository.findTop3ByUserIdOrderByEntrytimeDesc(user.getId());
    }


}
