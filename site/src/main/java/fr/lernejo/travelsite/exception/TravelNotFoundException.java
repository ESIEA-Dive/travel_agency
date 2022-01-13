package fr.lernejo.travelsite.exception;

public class TravelNotFoundException extends RuntimeException {

    public TravelNotFoundException(String message) {
        super(message);
    }
}
