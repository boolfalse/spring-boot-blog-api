package am.github.springbootblogapi.exceptions;

import am.github.springbootblogapi.payloads.ErrorDetails;
import am.github.springbootblogapi.utils.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // CUSTOM EXCEPTION HANDLERS
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ) {
        return new ResponseEntity<>(new ErrorDetails(
                Helper.getNowDateFormatted(), // new Date()
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        ), HttpStatus.NOT_FOUND);
    }

    // NO EXCEPTION HANDLER FOUND
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorDetails> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            WebRequest webRequest
    ) {
        return new ResponseEntity<>(new ErrorDetails(
                Helper.getNowDateFormatted(), // new Date()
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // GLOBAL EXCEPTION HANDLER
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ) {
        return new ResponseEntity<>(new ErrorDetails(
                Helper.getNowDateFormatted(), // new Date()
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
