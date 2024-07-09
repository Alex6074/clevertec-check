package ru.clevertec.check.validation;

import ru.clevertec.check.core.Check;
import ru.clevertec.check.core.Product;
import ru.clevertec.check.jdbc.JDBCRepository;

import java.util.Map;
import java.util.Optional;

public class BadRequestValidation implements CheckValidation {
    @Override
    public Optional<ValidationError> validate(Check check) {
        Map<Product, Integer> products = check.getProducts();
        if (products.isEmpty() || products.containsKey(null) || check.getBalanceDebitCard() == Double.MIN_VALUE) {
            return Optional.of(new ValidationError("BAD REQUEST",
                    "Arguments filled in incorrectly (missing products)"));
        }

        JDBCRepository repository = check.getRepository();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if (entry.getValue() > repository.findProductById(entry.getKey().getId()).getQuantity()) {
                return Optional.of(new ValidationError("BAD REQUEST",
                        "Arguments filled in incorrectly (quantity errors)"));
            }
        }

        return Optional.empty();
    }
}
