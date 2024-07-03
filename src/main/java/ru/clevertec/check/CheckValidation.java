package ru.clevertec.check;

import java.util.Optional;

public interface CheckValidation {
    Optional<ValidationError> validate(Check check);
}
