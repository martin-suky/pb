package cz.suky.pb.server.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by none_ on 04/13/16.
 */
public abstract class AbstractServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public AbstractServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AbstractServiceException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
