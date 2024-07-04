package ru.clevertec.check;

import java.util.Optional;

public class NotEnoughMoneyValidation implements CheckValidation{
    @Override
    public Optional<ValidationError> validate(Check check) {
        return check.getBalanceDebitCard() < check.getTotalAmount() - check.getTotalDiscount()
                ? Optional.of(new ValidationError("400", "NOT ENOUGH MONEY"))
                : Optional.empty();
    }
}
