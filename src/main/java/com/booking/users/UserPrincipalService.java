package com.booking.users;

import com.booking.exceptions.PasswordMismatchException;
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

    public void changePassword(String username, String newpassword, String oldpassword) throws PasswordMismatchException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!oldpassword.equals(user.getPassword())) throw new PasswordMismatchException("Old password incorrect");

        user.setPassword(newpassword);
        userRepository.save(user);
    }
}
