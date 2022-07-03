package com.booking.users;

import com.booking.exceptions.OldPasswordIncorrectException;
import com.booking.exceptions.OldThreePasswordMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class UserPrincipalServiceTest {

    private String username;
    private String oldpassword;
    private UserPrincipalService userPrincipalService;
    private UserRepository userRepository;
    private User user;
    @BeforeEach
    public void beforeEach(){
        userRepository = mock(UserRepository.class);
    }



}