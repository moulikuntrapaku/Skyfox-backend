package com.booking.validators.enumNamePattern;

public class EnumValidationException extends Exception {

    private String enumValue = null;
    private String enumName = null;

    public EnumValidationException(String enumValue, String enumName) {
        super(enumValue);

        this.enumValue = enumValue;
        this.enumName = enumName;
    }

    public EnumValidationException(String enumValue, String enumName, Throwable cause) {
        super(enumValue, cause);

        this.enumValue = enumValue;
        this.enumName = enumName;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public String getEnumName() {
        return enumName;
    }

    @Override
    public String getMessage() {
        return "Invalid value '" + getEnumValue() + "' for " + getEnumName();
    }
}
