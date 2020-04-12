package com.demo.aws.elasticsearch.data.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorDto {
    private String message;
    private String details;
    private HttpStatus statusCode;
    private Date timestamp;

    public ErrorDto(String message, String details, HttpStatus statusCode) {
        this.message = message;
        this.details = details;
        this.timestamp = new Date();
        this.statusCode = statusCode;
    }

    public ErrorDto(String message, HttpStatus statusCode) {
        this.message = message;
        this.details = message;
        this.timestamp = new Date();
        this.statusCode = statusCode;
    }

}