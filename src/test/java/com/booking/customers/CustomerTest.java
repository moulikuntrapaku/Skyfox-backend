package com.booking.customers;

import com.booking.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.passay.PasswordData.Origin.User;

public class CustomerTest {

    private Validator validator;
    private User user;

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void should_not_allow_customer_name_to_be_blank() {
        final Customer customer = new Customer("", "9099234568","ark@gmail.com");

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Customer name must be provided"));
    }

    @Test
    public void should_allow_phone_number_only_10_digits() {
        final Customer customer = new Customer("Customer 1", "999332","ark@gmail.com");
        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Phone number must have exactly 10 digits"));
    }

    @Test
    public void should_not_allow_blank_phone_number() {
        final Customer customer = new Customer("Customer 1", "","ark@gmail.com");

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Phone number must be provided"));
    }


    @Test
    public void should_follow_valid_email() {
        final Customer customer = new Customer("Customer 1", "1234567890","arkgmail.com");

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Email id should be valid"));
    }
    @Test
    public void should_not_allow_blank_email() {
        final Customer customer = new Customer("Customer 1", "1234567890","");

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Email id should be valid"));
    }

}
