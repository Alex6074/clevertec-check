package ru.clevertec.check;

import java.util.Map;
import java.util.Optional;

public class BadRequestValidation implements CheckValidation {
    @Override
    public Optional<ValidationError> validate(Check check) {
        if (check.getProducts().isEmpty() || check.getProducts().containsKey(null) || check.getBalanceDebitCard() == Double.MIN_VALUE) {
            return Optional.of(new ValidationError("400", "BAD REQUEST"));
        }

        Map<Integer, Product> stock = check.getProductFactory().getProducts();
        for (Map.Entry<Product, Integer> entry : check.getProducts().entrySet()) {
            if (entry.getValue() > stock.get(entry.getKey().getId()).getQuantity()) {
                return Optional.of(new ValidationError("400", "BAD REQUEST"));
            }
        }

        return Optional.empty();
    }
}
