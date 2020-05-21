package com.booking.movieGateway.models;

import com.booking.exceptions.EnumValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum MovieStatus {
    RUNNING("RUNNING"), UPCOMING("UPCOMING");
    private final String status;

    MovieStatus(String status) {
        this.status = status;
    }

    String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

    @JsonCreator
    public static MovieStatus create(String value) throws EnumValidationException {
        if (value == null) {
            throw new EnumValidationException(null, MovieStatus.class.toString());
        }
        for (MovieStatus v : values()) {
            if (value.equals(v.getStatus())) {
                return v;
            }
        }
        throw new EnumValidationException(value, MovieStatus.class.toString());
    }
}
