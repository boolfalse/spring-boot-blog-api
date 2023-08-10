package am.github.springbootblogapi.exceptions;

import am.github.springbootblogapi.payloads.ErrorResponse;
import am.github.springbootblogapi.utils.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                Helper.getNowDateFormatted()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException exception
            // WebRequest webRequest
    ) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(), // webRequest.getDescription(false),
                Helper.getNowDateFormatted()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
//        // create a hashmap
//        Map<String, String> errors = new HashMap<>();
//        // loop over the possible errors and push them into the hashmap to get the k:v list of errors
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//            String field = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(field, message);
//        });
//        // As we show the response "message" as a string,
//        // we are just getting the first pair of "errors" Map.
//        // Alternatively we could modify "ErrorDetails" class,
//        // and add some array private field there called like "messages".
//        Map.Entry<String,String> entry = errors.entrySet().iterator().next();
//        String errorMessage = entry.getValue(); // String key = entry.getKey();

        BindingResult bindingResult = exception.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse("Validation failed!");

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                Helper.getNowDateFormatted()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage(),
                Helper.getNowDateFormatted()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                Helper.getNowDateFormatted()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
