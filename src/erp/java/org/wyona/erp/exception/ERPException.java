package org.wyona.erp.exception;

/**
 * General ERP exception.
 */
public class ERPException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ctor.
     */
    public ERPException() {
        super();
    }

    /**
     * Ctor.
     * @param message The message.
     */
    public ERPException(String message) {
        super(message);
    }

    /**
     * Ctor.
     * @param message The message.
     * @param cause The cause.
     */
    public ERPException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Ctor.
     * @param cause The cause.
     */
    public ERPException(Throwable cause) {
        super(cause);
    }

}
