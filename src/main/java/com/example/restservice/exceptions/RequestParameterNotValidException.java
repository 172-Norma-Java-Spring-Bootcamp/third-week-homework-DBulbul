package com.example.restservice.exceptions;

public class RequestParameterNotValidException extends WeatherControllerException{
    public RequestParameterNotValidException() {
        super();
    }

    public RequestParameterNotValidException(String message) {
        super(message);
    }

    public RequestParameterNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
