package ru.clevertec.check.validation;

import ru.clevertec.check.core.Check;

import java.util.Optional;

public interface CheckValidation {
    Optional<ValidationError> validate(Check check);
}
