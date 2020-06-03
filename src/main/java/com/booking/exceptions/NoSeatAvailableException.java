package com.booking.exceptions;

public class NoSeatAvailableException extends Throwable {
    public NoSeatAvailableException(String message) {
        super(message);
    }
}
