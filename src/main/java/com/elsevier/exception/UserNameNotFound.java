package com.elsevier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNameNotFound extends RuntimeException {
    public UserNameNotFound(String message) {
        super(message);
    }
}
