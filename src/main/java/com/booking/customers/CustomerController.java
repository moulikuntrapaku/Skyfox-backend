package com.booking.customers;

import com.booking.exceptions.CustomerAlreadyExistsException;
import com.booking.validations.ValidPassword;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Api(tags = "Customer")
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping(value = "/customer/add", consumes = {"application/json"})
    public ResponseEntity<String> addCustomer( @RequestBody Customer customer) throws CustomerAlreadyExistsException {
        customerService.add(customer);
        return new ResponseEntity<>("Congratulations , your account has been successfully created", HttpStatus.CREATED);
    }
}
