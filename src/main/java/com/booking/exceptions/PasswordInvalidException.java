package com.booking.exceptions;

import com.booking.validations.PasswordConstraintValidator;

public class PasswordInvalidException extends Exception {
    PasswordInvalidException(String message){
        super(message);
    }
}

