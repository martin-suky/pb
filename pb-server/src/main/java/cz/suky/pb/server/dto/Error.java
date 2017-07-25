package cz.suky.pb.server.dto;

import org.springframework.http.HttpStatus;

/**
 * Created by none_ on 04/14/16.
 */
public class Error {
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public Error(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
