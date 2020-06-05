package org.springframework.data.ssdb.client;

/**
 * Create by tuke on 2019-05-22
 */
public class SSDBException extends RuntimeException {

    public SSDBException() {
        super();
    }

    public SSDBException(String message) {
        super(message);
    }

    public SSDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSDBException(Throwable cause) {
        super(cause);
    }

}
