package com.epam.esm.handler;

import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityDisabledException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityUsedException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.messages.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/** The type Entity exception handler. */
@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

  /** The constant TAG_CODE. */
  private static final String TAG_CODE = "01";

  /** The constant CERTIFICATE_CODE. */
  private static final String CERTIFICATE_CODE = "00";

  /** The constant ORDER_CODE. */
  private static final String ORDER_CODE = "10";

  /** The constant USER_CODE. */
  private static final String USER_CODE = "11";

  /** The constant DEFAULT_CODE. */
  private static final String DEFAULT_CODE = "22";

  /** The constant PARAMS_CONVERTABLE_ERROR_MESSAGE_. */
  private static final String PARAMS_CONVERTABLE_ERROR_MESSAGE_KEY =
      "handler.param_should_be_convertable";

  /** The Translator. */
  private Translator translator;

  /**
   * Instantiates a new Entity exception handler.
   *
   * @param translator the translator
   */
  @Autowired
  public EntityExceptionHandler(Translator translator) {
    this.translator = translator;
  }

  /**
   * Handle validation exception response entity.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = ValidatorException.class)
  protected ResponseEntity<?> handleValidationException(
      ValidatorException exception, WebRequest request) {
    String errorCode = getErrorCode(HttpStatus.BAD_REQUEST.value(), exception.getEntityClass());
    ErrorResponseBody errorResponseBody = new ErrorResponseBody(exception.getMessages(), errorCode);
    return handleExceptionInternal(
        exception, errorResponseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handle not found exception response entity.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = EntityNotFoundException.class)
  protected ResponseEntity<?> handleNotFoundException(
      EntityNotFoundException exception, WebRequest request) {
    ErrorResponseBody responseBody =
        getResponseBody(exception, exception.getEntityClass(), HttpStatus.NOT_FOUND.value());
    return handleExceptionInternal(
        exception, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  /**
   * Handle entity used exception response entity.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = EntityUsedException.class)
  protected ResponseEntity<?> handleEntityUsedException(
      EntityUsedException exception, WebRequest request) {
    ErrorResponseBody responseBody =
        getResponseBody(exception, exception.getEntityClass(), HttpStatus.CONFLICT.value());
    return handleExceptionInternal(
        exception, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  /**
   * Handle already exists response entity.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = EntityAlreadyExistException.class)
  protected ResponseEntity<?> handleAlreadyExists(
      EntityAlreadyExistException exception, WebRequest request) {
    ErrorResponseBody responseBody =
        getResponseBody(exception, exception.getEntityClass(), HttpStatus.CONFLICT.value());
    return handleExceptionInternal(
        exception, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  /**
   * Handle entity disabled response entity.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = EntityDisabledException.class)
  protected ResponseEntity<?> handleEntityDisabled(
      EntityDisabledException exception, WebRequest request) {
    ErrorResponseBody responseBody =
        getResponseBody(exception, exception.getEntityClass(), HttpStatus.CONFLICT.value());
    return handleExceptionInternal(
        exception, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  /**
   * Handle bad id response entity.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<?> handleBadId(
      MethodArgumentTypeMismatchException exception, WebRequest request) {
    String message = translator.toLocale(PARAMS_CONVERTABLE_ERROR_MESSAGE_KEY);
    final ErrorResponseBody responseBody =
        new ErrorResponseBody(List.of(message), HttpStatus.BAD_REQUEST.value() + DEFAULT_CODE);
    return handleExceptionInternal(
        exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Gets response body.
   *
   * @param exception the exception
   * @param entityClass the entity class
   * @param code the code
   * @return the response body
   */
  private ErrorResponseBody getResponseBody(Exception exception, Class<?> entityClass, int code) {
    String errorCode = getErrorCode(code, entityClass);
    return new ErrorResponseBody(List.of(exception.getMessage()), errorCode);
  }

  /**
   * Gets error code.
   *
   * @param code the code
   * @param entityClass the entity class
   * @return the error code
   */
  private String getErrorCode(int code, Class<?> entityClass) {
    String errorCode = String.valueOf(code);
    switch (entityClass.getSimpleName()) {
      case "Tag":
        errorCode += TAG_CODE;
        break;
      case "Certificate":
        errorCode += CERTIFICATE_CODE;
        break;
      case "User":
        errorCode += USER_CODE;
        break;
      case "Order":
        errorCode += ORDER_CODE;
        break;
      default:
        errorCode += DEFAULT_CODE;
    }
    return errorCode;
  }
}
