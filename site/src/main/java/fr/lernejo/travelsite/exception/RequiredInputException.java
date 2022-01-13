package fr.lernejo.travelsite.exception;

public class RequiredInputException extends RuntimeException {

    public RequiredInputException(String message) {
        super(message);
    }
}
