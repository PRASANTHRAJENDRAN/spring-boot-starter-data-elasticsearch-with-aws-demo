package com.demo.aws.elasticsearch.data.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom Exception for
 */
public class ApplicationException extends RuntimeException {
    private final transient ErrorDto apiError;

    public ApplicationException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.apiError = new ErrorDto(status.name(), status);
    }

    public ApplicationException(HttpStatus status) {
        this.apiError = new ErrorDto(status.name(), status);
    }

    public ApplicationException(ErrorDto apiError, String errorMessage) {
        super(errorMessage);
        this.apiError = apiError;
    }

    public ErrorDto getApiError() {
        return this.apiError;
    }
}
