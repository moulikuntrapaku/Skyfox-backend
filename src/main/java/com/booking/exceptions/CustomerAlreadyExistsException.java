package com.booking.exceptions;

public class CustomerAlreadyExistsException extends Throwable {

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
