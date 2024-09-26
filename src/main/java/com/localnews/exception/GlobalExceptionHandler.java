package com.localnews.exception;

import com.localnews.dto.ExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public ResponseEntity<ExceptionWrapper> handleGenericExceptions(Throwable exception, HttpServletRequest request) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionWrapper.builder()
                        .success(false)
                        .message("Action failed: An error occurred!")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ExceptionWrapper> handleNotFoundException(CityNotFoundException e, HttpServletRequest request){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionWrapper.builder()
                        .success(false)
                        .message(e.getMessage())
                        .code(HttpStatus.NOT_FOUND.value())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(GptResponseFailedException.class)
    public ResponseEntity<ExceptionWrapper> handleResponseFailedException(CityNotFoundException e, HttpServletRequest request){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionWrapper.builder()
                        .success(false)
                        .message(e.getMessage())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .path(request.getRequestURI())
                        .build());
    }


}
