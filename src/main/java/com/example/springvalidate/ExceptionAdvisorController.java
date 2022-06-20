package com.example.springvalidate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvisorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validateHandler(MethodArgumentNotValidException e, ServletWebRequest webRequest) throws MethodArgumentNotValidException {
        String path = webRequest.getRequest().getRequestURI();
        List<FieldErrorResponse> collect = e.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(f -> f.getField()))
                .entrySet()
                .stream()
                .map(FieldErrorResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(path, HttpStatus.BAD_REQUEST.value(), collect));
    }

    @Getter @RequiredArgsConstructor
    public static class ErrorResponse {
        private String timestamp;
        private String path;
        private int state;
        private List<FieldErrorResponse> errors;

        public ErrorResponse(String path, int state, List<FieldErrorResponse> errors) {
            this.path = path;
            this.state = state;
            this.errors = errors;
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
    @Getter
    public static class FieldErrorResponse {
        private String field;
        private String message;

        public FieldErrorResponse(Map.Entry<String, List<FieldError>> entry){
            this.field = entry.getKey();
            this.message = entry.getValue().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        }
    }
}
