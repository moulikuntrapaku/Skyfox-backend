package com.booking.exceptions;

public class NewAndOldPasswordMatchException extends Exception {
    public NewAndOldPasswordMatchException(String message) {
        super(message);
    }
}
