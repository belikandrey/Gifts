package com.epam.esm.handler;

import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityUsedException;
import com.epam.esm.exception.ValidatorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = ValidatorException.class)
  protected ResponseEntity<?> handleValidationException(
      ValidatorException exception, WebRequest request) {
    String message = exception.getMessage();
    return handleExceptionInternal(
        exception, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = EntityNotFoundException.class)
  protected ResponseEntity<?> handleNotFoundException(
      EntityNotFoundException exception, WebRequest request) {
    String message = exception.getMessage();
    return handleExceptionInternal(
        exception, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = EntityUsedException.class)
  protected ResponseEntity<?> handleEntityUsedException(
      EntityUsedException exception, WebRequest request) {
    String message = exception.getMessage();
    return handleExceptionInternal(
        exception, message, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value = EntityAlreadyExistException.class)
  protected ResponseEntity<?> handleAlreadyExists(
      EntityAlreadyExistException exception, WebRequest request) {
    String message = exception.getMessage();
    return handleExceptionInternal(
        exception, message, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }


  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<?> handleBadId(
          MethodArgumentTypeMismatchException exception, WebRequest request) {
    String message = "Id should be convertible into number";
    return handleExceptionInternal(
            exception, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
