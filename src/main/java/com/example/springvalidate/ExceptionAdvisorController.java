package com.example.springvalidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvisorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validateHandler(MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
        System.out.println("Exception Handler");
        Map<String, List<SimpleFiledError>> collect = e.getFieldErrors()
                .stream()
                .map(f -> new SimpleFiledError(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.groupingBy(f -> f.getField()));
        throw e;
    }
    
    @AllArgsConstructor @Getter
    public static class SimpleFiledError {
        private String field;
        private String message;
    }
}
