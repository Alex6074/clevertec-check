package ru.clevertec.check;

import ru.clevertec.check.core.Check;
import ru.clevertec.check.validation.CheckValidator;
import ru.clevertec.check.validation.ValidationError;

import java.util.List;

public class CheckRunner {

    public static void main(String[] args) {
        CheckValidator validator = new CheckValidator();

        String saveToFile = null;
        String url = null;
        String username = null;
        String password = null;

        for (String arg : args) {
            if (arg.startsWith("saveToFile=")) {
                saveToFile = arg.split("=")[1];
            } else if (arg.startsWith("datasource.url=")) {
                url = arg.split("=")[1];
            } else if (arg.startsWith("datasource.username=")) {
                username = arg.split("=")[1];
            } else if (arg.startsWith("datasource.password=")) {
                password = arg.split("=")[1];
            }
        }

        if (saveToFile == null) {
            validator.printErrors("result.csv", List.of(new ValidationError("BAD REQUEST", "Invalid arguments")));
            return;
        } else if (url == null || username == null || password == null) {
            validator.printErrors(saveToFile, List.of(new ValidationError("BAD REQUEST", "Invalid arguments")));
            return;
        }

        Check check = new Check(args, url, username, password);

        List<ValidationError> errors = validator.validate(check);
        if (!errors.isEmpty()) {
            validator.printErrors(saveToFile, errors);
        } else {
            check.printCheck();
            check.saveCheckToFile(saveToFile);
        }
    }
}
