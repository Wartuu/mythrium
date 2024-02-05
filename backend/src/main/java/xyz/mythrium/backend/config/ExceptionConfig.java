package xyz.mythrium.backend.config;


import xyz.mythrium.backend.json.output.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    public ExceptionConfig() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> details = new ArrayList<>();


        ApiError error = new ApiError(false, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, error.status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder information = new StringBuilder();
        information.append(ex.getMethod()).append(" is not supported. Implementend methods are: ");
        ex.getSupportedHttpMethods().forEach(httpMethod -> information.append(information + " "));

        ApiError error = new ApiError(false, information.toString(), HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(error, error.status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error = new ApiError(false, "handler for " + ex.getRequestURL() + " does not exist", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(error, error.status);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error;

        if(ex.getResourcePath().startsWith("api/"))
            error = new ApiError(false, "url for this API is incorrect or not found: " + ex.getResourcePath(), HttpStatus.NOT_FOUND);
        else
            error = new ApiError(false, "resource does not exist: " + ex.getResourcePath(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(error, error.status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle(Exception e, WebRequest webRequest) {
        ApiError error = new ApiError(false, "server error", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, error.status);
    }
}
