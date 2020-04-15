package com.booking.shows;

import com.booking.validators.enumNamePattern.EnumValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShowStatus {
    RUNNING("RUNNING"), UPCOMING("UPCOMING");
    private final String status;

    ShowStatus(String status) {
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
    public static ShowStatus create (String value) throws EnumValidationException {
        if(value == null) {
            throw new EnumValidationException(value, "ShowStatus");
        }
        for(ShowStatus v : values()) {
            if(value.equals(v.getStatus())) {
                return v;
            }
        }
        throw new EnumValidationException(value, "ShowStatus");
    }
    }
