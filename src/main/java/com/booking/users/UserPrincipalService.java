package com.booking.users;

import com.booking.exceptions.OldThreePasswordMatchException;
import com.booking.exceptions.OldPasswordIncorrectException;
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

    public void changePassword(String username, String newpassword, String oldpassword) throws OldPasswordIncorrectException, OldThreePasswordMatchException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!oldpassword.equals(user.getPassword())) throw new OldPasswordIncorrectException("Old password incorrect");

        List<PasswordHistory> passwordHistories = passwordHistoryRepository.findTop3ByUserIdOrderByEntrytimeDesc(user.getId());
        for (PasswordHistory passwordHistory : passwordHistories){
            if (newpassword.equals(passwordHistory.getPassword())) {
                throw new OldThreePasswordMatchException("Password should not match with last three passwords");
            }

        }

        user.setPassword(newpassword);
        user.getPasswordHistories().add(new PasswordHistory(user.getId(), newpassword));
        userRepository.save(user);

    }

    public List<PasswordHistory> findPassHisById(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return passwordHistoryRepository.findTop3ByUserIdOrderByEntrytimeDesc(user.getId());
    }


}
