package com.currencyexchange.controller;

import com.currencyexchange.exceptions.ApiError;
import com.currencyexchange.exceptions.ExternalMethodFailedException;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.exceptions.UnprocessableEntityException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.money.UnknownCurrencyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        log.error("ExceptionHandler MethodArgumentNotValidException: ", exception);
        List<String> errors = new ArrayList<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors())
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());

        return ResponseEntity.badRequest().body(new ApiError(exception.getClass().getSimpleName(), errors));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception, WebRequest request) {
        log.error("ExceptionHandler MethodArgumentTypeMismatchException: ", exception);
        String error = exception.getName() + " should be of type " + Objects.requireNonNull(exception.getRequiredType()).getName();

        return ResponseEntity.badRequest().body(new ApiError(exception.getClass().getSimpleName(), List.of(error)));
    }

    @ExceptionHandler(UnknownCurrencyException.class)
    protected ResponseEntity<?> UnknownCurrencyException(UnknownCurrencyException exception, WebRequest request) {
        log.error("ExceptionHandler UnknownCurrencyException: ", exception);
        return ResponseEntity.unprocessableEntity().body(new ApiError(exception.getClass().getSimpleName(), Arrays.asList(exception.getMessage())));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> bussinessLogicError(Exception exception) {
        log.error("ExceptionHandler NotFoundException or UnprocessableEntityException: ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(exception.getClass().getSimpleName(), List.of(exception.getMessage())));
    }

    @ExceptionHandler(value = UnprocessableEntityException.class)
    protected ResponseEntity<?> handleUnprocessableEntityException(UnprocessableEntityException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(new ApiError(exception.getClass().getSimpleName(), List.of(exception.getMessage())));
    }

    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<Object> handleFeignClientException(Exception exception) {
        return ResponseEntity.internalServerError().body(new ApiError(exception.getClass().getSimpleName(), List.of(exception.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnknownError(Exception exception) {
        return ResponseEntity.internalServerError().body(new ApiError(exception.getClass().getSimpleName(), List.of(exception.getMessage())));
    }

    @ExceptionHandler(value = ExternalMethodFailedException.class)
    protected ResponseEntity<?> handleExternalMethodFailedException(ExternalMethodFailedException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(new ApiError(exception.getClass().getSimpleName(), List.of(exception.getMessage())));
    }
}