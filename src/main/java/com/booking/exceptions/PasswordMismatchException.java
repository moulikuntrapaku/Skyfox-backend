package com.booking.exceptions;

public class PasswordMismatchException extends Throwable {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
