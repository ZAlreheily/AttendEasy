package com.example.AttendEasy.exceptions;

public class TimeMissmatchException extends Exception{

    public TimeMissmatchException() {
        super();
    }

    public TimeMissmatchException(String message) {
        super(message);
    }

    public TimeMissmatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeMissmatchException(Throwable cause) {
        super(cause);
    }

    protected TimeMissmatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
