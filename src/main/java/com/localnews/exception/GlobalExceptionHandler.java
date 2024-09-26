package com.localnews.exception;

import com.localnews.annotation.DefaultExceptionMessage;
import com.localnews.dto.exception.DefaultExceptionMessageDto;
import com.localnews.dto.exception.ExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Throwable.class, Exception.class, RuntimeException.class})
    public ResponseEntity<ExceptionWrapper> genericExceptionHandler(Throwable e, HandlerMethod handlerMethod, HttpServletRequest request) {

        Optional<DefaultExceptionMessageDto> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());
        if (defaultMessage.isPresent() && !ObjectUtils.isEmpty(defaultMessage.get().getMessage())) {
            ExceptionWrapper response = ExceptionWrapper
                    .builder()
                    .success(false)
                    .message("Action failed: An error occurred!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ExceptionWrapper.builder().success(false).message(e.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).path(request.getRequestURI()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Optional<DefaultExceptionMessageDto> getMessageFromAnnotation(Method method) {
        DefaultExceptionMessage defaultExceptionMessage = method.getAnnotation(DefaultExceptionMessage.class);
        if (defaultExceptionMessage != null) {
            DefaultExceptionMessageDto defaultExceptionMessageDto = DefaultExceptionMessageDto
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }


}
