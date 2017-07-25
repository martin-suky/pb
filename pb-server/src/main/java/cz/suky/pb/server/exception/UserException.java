package cz.suky.pb.server.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by none_ on 04/12/16.
 */
public class UserException extends AbstractServiceException {

    public UserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public UserException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }

    public static UserException notAuthorized() {
        return new UserException(HttpStatus.UNAUTHORIZED, "You are not authorized to access this resource.");
    }

    public static UserException userError(String error) {
        return new UserException(HttpStatus.BAD_REQUEST, error);
    }
}
