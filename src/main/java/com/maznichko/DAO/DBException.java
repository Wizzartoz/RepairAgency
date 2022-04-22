package com.maznichko.DAO;

public class DBException extends Exception {
    String message;
    Throwable cause;

    public DBException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;


    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
