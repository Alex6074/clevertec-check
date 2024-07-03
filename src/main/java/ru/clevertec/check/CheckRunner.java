package ru.clevertec.check;

import java.util.List;

public class CheckRunner {

    public static void main(String[] args) {
        CheckValidator validator = new CheckValidator();
        Check check = new Check(args);

        List<ValidationError> errors = validator.validate(check);
        if (!errors.isEmpty()) {
            validator.printErrors("result.csv", errors);
        } else {
            check.printCheck();
            check.saveCheckToFile("result.csv");
        }
    }
}