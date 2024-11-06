package org.example.groupservice.exceptions;

import jakarta.servlet.annotation.HttpConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateGroupException extends RuntimeException
{
    public DuplicateGroupException(String message)
    {
        super(message);
    }
}
