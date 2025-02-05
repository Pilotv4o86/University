package com.example.studentservice.exception;

import org.example.userservice.controller.UserController;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {

}
