package com.example.restservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestServiceControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(WeatherControllerException.class)
    public ResponseEntity<WeatherApiError> onNullPointerExceptionHandled(WeatherControllerException exception) {
        WeatherApiError error = new WeatherApiError(exception.getMessage(), false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String parameterName = ex.getParameter().getParameterName();
        Class<?> parameterType = ex.getParameter().getParameterType();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String message;
        if (!CollectionUtils.isEmpty(allErrors)) {
            message = allErrors.get(0).getDefaultMessage();
        } else {
            message = ex.getMessage();
        }
        log.info("{},{},{}", parameterName, parameterType, message);
        WeatherApiError error = new WeatherApiError(message, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String parameterName = ex.getParameterName();
        String parameterType = ex.getParameterType();
        log.info("Parameter Name -> {}, Parameter Type -> {}", parameterName, parameterType);
        WeatherApiError error = new WeatherApiError("Parameter Not Valid", false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }

}