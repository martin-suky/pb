package cz.suky.pb.server.controller;

import cz.suky.pb.server.dto.Error;
import cz.suky.pb.server.exception.AbstractServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * Created by none_ on 04/13/16.
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handleError(AbstractServiceException ex) throws IOException {
        return ResponseEntity.status(ex.getHttpStatus()).body(new Error(ex.getHttpStatus(), ex.getMessage()));
    }
}
