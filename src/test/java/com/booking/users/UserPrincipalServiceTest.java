package com.booking.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

//    @Test
//    public void should_change_password(){
//        userPrincipalService.changePassword(user.getUsername(),"NewPassword1!", user.getPassword());
//        user = new User("testuser","password");
//        verify(userRepository).save(user);
//    }

    @Test
    public void doNothing(){
        assertTrue(true);
    }

}