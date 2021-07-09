package edu.summer.java;

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
