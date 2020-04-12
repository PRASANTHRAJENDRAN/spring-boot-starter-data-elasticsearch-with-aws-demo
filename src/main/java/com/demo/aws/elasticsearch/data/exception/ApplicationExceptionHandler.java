package com.demo.aws.elasticsearch.data.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

/**
 * Exception handler to handle all the custom application exception {@link ApplicationException} and general {@link Exception}.
 * Apart from all the custom exception we will delicate it to ResponseEntityExceptionHandler{@link #handleApiException(ApplicationException, WebRequest)}
 * method to get the pertinent response entity.
 */
@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ErrorDto> handleApiException(ApplicationException ex, WebRequest req) {
        return new ResponseEntity<>(ex.getApiError(), ex.getApiError().getStatusCode());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDto> handleExceptions(Exception ex, WebRequest req) {
        log.error("Exception : ", ex);
        try {
            ResponseEntity<Object> responseEntity = super.handleException(ex, req);
            HttpStatus status = Objects.isNull(responseEntity) ? HttpStatus.INTERNAL_SERVER_ERROR : responseEntity.getStatusCode();
            return new ResponseEntity<>(new ErrorDto(ex.getMessage(), status), status);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}