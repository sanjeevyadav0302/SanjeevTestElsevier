package com.elsevier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GitServiceException extends RuntimeException {

    public GitServiceException(String message) {
        super(message);
    }
}
