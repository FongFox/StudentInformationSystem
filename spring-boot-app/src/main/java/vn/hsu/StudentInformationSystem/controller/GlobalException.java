package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.hsu.StudentInformationSystem.service.dto.RestResponse;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            EntityNotFoundException.class,
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            JwtException.class,
            MissingRequestCookieException.class
    })
    public ResponseEntity<RestResponse<Object>> handleException(Exception exception) {
        HttpStatus status;

        if (exception instanceof EntityNotFoundException || exception instanceof UsernameNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof BadCredentialsException || exception instanceof JwtException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(status.value());
        restResponse.setMessage("Call API Failed!");
        restResponse.setError(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setMessage(ex.getBody().getDetail());

//        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList());
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }
        restResponse.setError(errors.size() > 1 ? errors : errors.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }
}
