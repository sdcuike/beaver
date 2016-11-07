package com.doctor.commons.mail;

public class EmailException extends RuntimeException {

    private static final long serialVersionUID = -8805575403417644318L;

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }

}
