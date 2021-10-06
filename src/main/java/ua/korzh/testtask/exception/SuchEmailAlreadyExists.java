package ua.korzh.testtask.exception;

public class SuchEmailAlreadyExists extends RuntimeException {

    public SuchEmailAlreadyExists(String message) {
        super(message);
    }
}
