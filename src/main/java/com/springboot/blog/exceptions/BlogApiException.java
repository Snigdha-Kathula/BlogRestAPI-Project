package com.springboot.blog.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class BlogApiException extends Exception{
    private HttpStatus status;
    private String message;

    public BlogApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
