package com.booking.customers;

import com.booking.exceptions.CustomerAlreadyExistsException;
import com.booking.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    CustomerRepository customerRepository;
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }


    public void add(CustomerDTO customerDTO) throws CustomerAlreadyExistsException {
        if ((userRepository.findByUsername(customerDTO.getUser().getUsername())).isPresent())
            throw new CustomerAlreadyExistsException("User with given username already exist");
        String encryptPwd = bCryptPasswordEncoder.encode(customerDTO.getUser().getPassword());
        customerDTO.getUser().setPassword(encryptPwd);
        customerRepository.save(new Customer(customerDTO.getName(), customerDTO.getPhoneNumber(), customerDTO.getEmail(),customerDTO.getUser()));

    }
}

