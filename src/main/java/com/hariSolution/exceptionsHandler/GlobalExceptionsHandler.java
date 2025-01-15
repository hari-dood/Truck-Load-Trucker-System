package com.hariSolution.exceptionsHandler;

import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.model.DataResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionsHandler {

    private final DataResponseMapper responseService;

    // Handle validation errors
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorList = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            errorList.add(error.getDefaultMessage());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errors", errorList);
        return responseService.createResponse(data, "Validation Error", 400, HttpStatus.BAD_REQUEST, UUID.randomUUID().toString());
    }

    // Handle duplicate key exceptions (e.g., unique constraint violations)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public DataResponse handleDuplicateKeyException(DuplicateKeyException ex) {
        Map<String, Object> data = Map.of("message", "Duplicate key error: " + ex.getMessage());
        return responseService.createResponse(data, "Duplicate Key Error", 409, HttpStatus.CONFLICT, UUID.randomUUID().toString());
    }

    // Handle data integrity violations
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public DataResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> data = Map.of("message", "Data integrity violation: " + ex.getMessage());
        return responseService.createResponse(data, "Data Integrity Violation", 400, HttpStatus.BAD_REQUEST, UUID.randomUUID().toString());
    }

    // Handle index out-of-bounds exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public DataResponse handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        Map<String, Object> data = Map.of("message", "Index out of bounds: " + ex.getMessage());
        return responseService.createResponse(data, "Internal Server Error", 500, HttpStatus.INTERNAL_SERVER_ERROR, UUID.randomUUID().toString());
    }

    // Handle "no such element" exceptions
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public DataResponse handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, Object> data = Map.of("message", ex.getMessage());
        return responseService.createResponse(data, "Not Found", 404, HttpStatus.NOT_FOUND, UUID.randomUUID().toString());
    }

    // Handle response status exceptions
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResponseStatusException.class)
    public DataResponse handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> data = Map.of("message", ex.getReason());
        return responseService.createResponse(data, "Error", ex.getStatusCode().value(), (HttpStatus) ex.getStatusCode(), UUID.randomUUID().toString());
    }

    // Handle missing servlet request parameters
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public DataResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, Object> data = Map.of("message", "Missing request parameter: " + ex.getParameterName());
        return responseService.createResponse(data, "Bad Request", 400, HttpStatus.BAD_REQUEST, UUID.randomUUID().toString());
    }

    // Handle generic exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public DataResponse handleGenericException(Exception ex) {
        Map<String, Object> data = Map.of("message", "Unexpected error: " + ex.getMessage());
        return responseService.createResponse(data, "Internal Server Error", 500, HttpStatus.INTERNAL_SERVER_ERROR, UUID.randomUUID().toString());
    }

    // Handle expired JWT tokens
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public DataResponse handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> data = Map.of("message", "JWT token has expired. Please log in again.");
        return responseService.createResponse(data, "Unauthorized", 401, HttpStatus.UNAUTHORIZED, UUID.randomUUID().toString());
    }

    // Handle malformed JWT tokens
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedJwtException.class)
    public DataResponse handleMalformedJwtException(MalformedJwtException ex) {
        Map<String, Object> data = Map.of("message", "Malformed JWT token. Please check the token format.");
        return responseService.createResponse(data, "Bad Request", 400, HttpStatus.BAD_REQUEST, UUID.randomUUID().toString());
    }
}
