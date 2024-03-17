package ru.job4j.todo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ServiceErrorHandler {

    @ExceptionHandler(Exception.class)
    public String handleCommonException(Exception e) {
        log.error("Internal error: {}", e.toString());
        return "error";
    }
}
