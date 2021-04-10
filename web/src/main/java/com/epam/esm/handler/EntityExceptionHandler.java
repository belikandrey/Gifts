package com.epam.esm.handler;

import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ValidatorException.class)
    protected ResponseEntity<?> handleValidationException(ValidatorException exception, WebRequest request){
        String message = "Validation exception";
        return handleExceptionInternal(exception, message,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(EntityNotFoundException exception, WebRequest request){
        String message = "Entity not found";
        return handleExceptionInternal(exception, message,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EntityAlreadyExistException.class)
    protected ResponseEntity<?> handleAlreadyExists(EntityAlreadyExistException exception, WebRequest request){
        String message = "Entity already exists";
        return handleExceptionInternal(exception, message,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}