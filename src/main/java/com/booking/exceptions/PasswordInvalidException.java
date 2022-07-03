package com.booking.exceptions;

import com.booking.validations.PasswordConstraintValidator;

public class PasswordInvalidException extends Throwable {
    PasswordInvalidException(String message){
        super(message);
    }
}

