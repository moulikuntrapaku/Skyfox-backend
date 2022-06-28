package com.booking.customers;

import com.booking.exceptions.CustomerAlreadyExistsException;
import com.booking.users.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    CustomerRepository customerRepository;
    UserRepository userRepository;


    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }




    public void add(Customer newCustomer) throws CustomerAlreadyExistsException {
        if ((userRepository.findByUsername(newCustomer.getUser().getUsername())).isPresent())
            throw new CustomerAlreadyExistsException("User with given username already exist");
        customerRepository.save(new Customer(newCustomer.getName(), newCustomer.getPhoneNumber(), newCustomer.getEmail(),newCustomer.getUser()));
    }
}
