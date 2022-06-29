package com.booking.users;

import com.booking.customers.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void should_follow_valid_password(){
        final User user = new User("Priya", "tomcat123!");

        final Set<ConstraintViolation<User>> violations = validator.validate(user);


        assertThat(violations.iterator().next().getMessage(), is("Password must be valid"));
    }

    @Test
    void should_not_allow_blank_password(){
        final User user = new User("Priya", "");

        final Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage(), is("Password must be valid"));
    }

}