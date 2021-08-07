package edu.summer.spring.elibrary.exception;

public class NotUniqueDataException extends Exception {
    private final String      data;

    public String getLoginData() {
        return data;
    }

    public NotUniqueDataException(String message, String data) {
        super(message);
        this.data = data;
    }
}