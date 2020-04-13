package com.booking.validators.enumNamePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Enum<?>> {
    private List<String> acceptedValues;
    private String violationMessage;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
        violationMessage = annotation.message();
    }

    @Override
    public boolean isValid(Enum<?> enumeration, ConstraintValidatorContext context) {
        if (enumeration == null) {
            return true;
        }

        if (!acceptedValues.contains(enumeration.name())) {
            context
                    .buildConstraintViolationWithTemplate(violationMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
