package com.elsevier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GitHubServiceException extends RuntimeException {
    public GitHubServiceException(String message) {
        super(message);
    }
}
