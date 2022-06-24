package com.booking.customers;

import com.booking.exceptions.CustomerAlreadyExistsException;
import com.booking.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @GetMapping("/customers")
//    public ResponseEntity<List<User>> getAllCustomers(){
//        List<Customer> customers = customerService.findAllCustomers();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping("/customers/add")
//    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
//        Customer newCustomer = customerService.addCustomer(customer);
//        return new ResponseEntity<>(newCustomer,HttpStatus.OK);
//    }
}
