package edu.summer.spring.elibrary.exception;

public class NotUniqueDataException extends Exception {
    private final String      data;

    public NotUniqueDataException(String message) {
        this(message, "");
    }

    public String getData() {
        return data;
    }

    public NotUniqueDataException(String message, String data) {
        super(message);
        this.data = data;
    }
}