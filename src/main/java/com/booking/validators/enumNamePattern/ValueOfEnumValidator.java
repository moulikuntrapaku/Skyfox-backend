package com.booking.validators.enumNamePattern;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Object> {
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
    public boolean isValid(Object enumeration, ConstraintValidatorContext context) {
        if (enumeration == null) {
            return true;
        }

        if (!acceptedValues.contains(enumeration.toString())) {
            context
                    .buildConstraintViolationWithTemplate(violationMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
