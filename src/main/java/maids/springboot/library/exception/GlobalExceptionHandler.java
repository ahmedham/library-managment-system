package maids.springboot.library.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation exceptions and return a map of field errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(   (error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        Map<String, Object> errorAttributes = createErrorAttributes(ex.getMessage(), request, HttpStatus.UNAUTHORIZED);
        errorAttributes.put("error", "The username or password is incorrect");

        return new ResponseEntity<>(errorAttributes, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Map<String, Object> errorAttributes = createErrorAttributes(ex.getMessage(), request, HttpStatus.FORBIDDEN);
        errorAttributes.put("error", "You are not authorized to access this resource");

        return new ResponseEntity<>(errorAttributes, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, Object>> handleSignatureException(SignatureException ex, WebRequest request) {
        Map<String, Object> errorAttributes = createErrorAttributes(ex.getMessage(), request, HttpStatus.FORBIDDEN);
        errorAttributes.put("error", "The JWT signature is invalid");

        return new ResponseEntity<>(errorAttributes, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        Map<String, Object> errorAttributes = createErrorAttributes(ex.getMessage(), request, HttpStatus.FORBIDDEN);
        errorAttributes.put("error", "The JWT token has expired");

        return new ResponseEntity<>(errorAttributes, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeExceptions(RuntimeException ex, WebRequest request) {
        Map<String, Object> errorAttributes = createErrorAttributes(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
        errorAttributes.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return new ResponseEntity<>(errorAttributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> createErrorAttributes(String message, WebRequest request, HttpStatus status) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("message", message);
        errorAttributes.put("details", request.getDescription(false));
        errorAttributes.put("status", status.value());

        return errorAttributes;
    }
}
