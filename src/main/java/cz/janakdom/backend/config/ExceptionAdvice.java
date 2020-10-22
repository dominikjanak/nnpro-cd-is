package cz.janakdom.backend.config;

import cz.janakdom.backend.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    protected ApiResponse<Void> handleGlobalException(Exception ex) {
        return new ApiResponse<Void>(400, ex.getMessage(), null);
    }
}
