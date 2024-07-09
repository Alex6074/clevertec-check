package ru.clevertec.check.validation;

import ru.clevertec.check.core.Check;

import java.util.Optional;

public class NotEnoughMoneyValidation implements CheckValidation{
    @Override
    public Optional<ValidationError> validate(Check check) {
        return check.getBalanceDebitCard() < check.getTotalAmount() - check.getTotalDiscount()
                ? Optional.of(new ValidationError("NOT ENOUGH MONEY",
                "Lack of funds (balance less than the amount on the check)"))
                : Optional.empty();
    }
}
