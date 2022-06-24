package com.booking.customers;

import com.booking.exceptions.CustomerAlreadyExistsException;
import com.booking.users.UserRepository;

import java.util.List;

public class CustomerService {

    CustomerRepository customerRepository;
    UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


//    public List<Customer> findAllCustomers() {
//        return customerRepository.findAll();
//    }
//
//    public Customer addCustomer(Customer newCustomer) throws CustomerAlreadyExistsException {
//        if (!(customerRepository.findByEmail(newCustomer.getEmail().isEmpty()))) {
//            throw new CustomerAlreadyExistsException("Customer already exists with this email id " + newCustomer.getEmail());
//        }
//        if (!(customerRepository.findByPhoneNo(newCustomer.getPhoneNumber().isEmpty()))) {
//            throw new CustomerAlreadyExistsException("Customer already exists with this phone no " + newCustomer.getPhoneNumber());
//        }
//
//        return customerRepository.save(newCustomer);
//    }
}
