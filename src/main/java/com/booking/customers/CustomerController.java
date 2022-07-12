package com.booking.customers;

import com.booking.exceptions.CustomerAlreadyExistsException;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@Api(tags = "Customer")
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping(value = "/customer/add", consumes = {"application/json"})
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDto) throws CustomerAlreadyExistsException {
        customerService.add(customerDto);
        return new ResponseEntity<>("Congratulations , your account has been successfully created", HttpStatus.CREATED);
    }

    @GetMapping(value="/customer")
    public ResponseEntity<Customer> getCustomer(@RequestParam String username){
        Customer customer = customerService.get(username);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
}
