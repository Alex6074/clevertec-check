package ru.clevertec.check;

import java.util.Map;
import java.util.Optional;

public class BadRequestValidation implements CheckValidation {
    @Override
    public Optional<ValidationError> validate(Check check) {
        if (check.getProducts().isEmpty() || check.getProducts().containsKey(null) || check.getBalanceDebitCard() == Double.MIN_VALUE) {
            return Optional.of(new ValidationError("BAD REQUEST",
                    "Arguments filled in incorrectly (missing products)"));
        }

        JDBCRepository repository = check.getRepository();
        for (Map.Entry<Product, Integer> entry : check.getProducts().entrySet()) {
            if (entry.getValue() > repository.findProductById(entry.getKey().getId()).getQuantity()) {
                return Optional.of(new ValidationError("BAD REQUEST",
                        "Arguments filled in incorrectly (quantity errors)"));
            }
        }

        return Optional.empty();
    }
}
