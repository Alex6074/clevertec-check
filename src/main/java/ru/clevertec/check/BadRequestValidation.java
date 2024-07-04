package ru.clevertec.check;

import java.util.Map;
import java.util.Optional;

public class BadRequestValidation implements CheckValidation {
    @Override
    public Optional<ValidationError> validate(Check check) {
        if (check.getProducts().isEmpty() || check.getProducts().containsKey(null) || check.getBalanceDebitCard() == Double.MIN_VALUE) {
            return Optional.of(new ValidationError("BAD REQUEST",
                    "Incorrect input data (arguments filled in incorrectly, quantity errors, missing products)"));
        }

        Map<Integer, Product> stock = check.getProductFactory().getProducts();
        for (Map.Entry<Product, Integer> entry : check.getProducts().entrySet()) {
            if (entry.getValue() > stock.get(entry.getKey().getId()).getQuantity()) {
                return Optional.of(new ValidationError("BAD REQUEST",
                        "Incorrect input data (arguments filled in incorrectly, quantity errors, missing products)"));
            }
        }

        return Optional.empty();
    }
}
