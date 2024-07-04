package ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class CheckRunner {

    public static void main(String[] args) {
        CheckValidator validator = new CheckValidator();

        String pathToFile = null;
        String saveToFile = null;

        for (String arg : args) {
            if (arg.startsWith("pathToFile=")) {
                pathToFile = arg.split("=")[1];
            } else if (arg.startsWith("saveToFile=")) {
                saveToFile = arg.split("=")[1];
            }
        }

        if (saveToFile == null) {
            validator.printErrors("result.csv", List.of(new ValidationError("BAD REQUEST",
                    "pathToFile and/or saveToFile arguments were not passed")));
            return;
        } else if (pathToFile == null) {
            validator.printErrors(saveToFile, List.of(new ValidationError("BAD REQUEST",
                    "pathToFile and/or saveToFile arguments were not passed")));
            return;
        }

        Check check = new Check(args, pathToFile);

        List<ValidationError> errors = validator.validate(check);
        if (!errors.isEmpty()) {
            validator.printErrors(saveToFile, errors);
        } else {
            check.printCheck();
            check.saveCheckToFile(saveToFile);
        }
    }
}