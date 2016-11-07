package com.doctor.commons.mail;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.07
 *         <p>
 */
public class EmailAddressException extends RuntimeException {

    private static final long serialVersionUID = -5791842352068839176L;

    public EmailAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAddressException(String message) {
        super(message);
    }

    public EmailAddressException(Throwable cause) {
        super(cause);
    }

}
