package org.example.groupservice.exception;

import org.example.groupservice.controller.GroupController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = GroupController.class)
public class GroupExceptionsHandler
{
    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> groupNotFound(RuntimeException exception)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateGroupException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> duplicateGroup(RuntimeException exception)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
