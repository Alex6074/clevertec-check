package ru.clevertec.check.validation;

import ru.clevertec.check.core.Check;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckValidator {
    private List<CheckValidation> errors;

    public CheckValidator() {
        errors = new ArrayList<>();
        errors.add(new BadRequestValidation());
        errors.add(new NotEnoughMoneyValidation());
    }

    public List<ValidationError> validate(Check check) {
        List<ValidationError> result = new ArrayList<>();
        for (CheckValidation validation : errors) {
            Optional<ValidationError> error = validation.validate(check);
            error.ifPresent(result::add);
        }
        return result;
    }

    public void printErrors(String filePath, List<ValidationError> errorList) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ERROR\n");
            System.out.println("ERROR");
            for (ValidationError error : errorList) {
                writer.write(error.getErrorCode() + "\n");
                System.out.println(error.getErrorCode() + ": " + error.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
