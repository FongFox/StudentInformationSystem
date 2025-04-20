package vn.hsu.StudentInformationSystem.controller.rest.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.hsu.StudentInformationSystem.model.RestResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setMessage("Call API Failed!");
        restResponse.setError(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleEntityNotFound(EntityNotFoundException exception) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setMessage("Call API Failed!");
        restResponse.setError(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResponse);
    }
}
