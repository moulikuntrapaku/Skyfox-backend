package com.booking.customers;

import com.booking.users.User;
import com.booking.users.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomerDTO {

    private String name;
    private String phoneNumber;
    private String email;


    public User getUser() {
        return user;
    }
    private User user;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

}
