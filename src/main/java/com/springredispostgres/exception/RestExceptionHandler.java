package com.springredispostgres.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @Getter
    private final Map<String, ErrorCode> errorCodeMapper = new HashMap<>();

    private ResponseEntity<Object> doHandleException(ErrorCode errorCode, Exception e) {
        logException(e);

        HttpStatus httpStatus = null;
        try {
            httpStatus = HttpStatus.valueOf(errorCode.getData().getHttpResponseCode());
        } catch (Exception exception) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorCode = ErrorCode.UNKNOWN_SERVER_ERROR;
        }

        ErrorCode.Data data = errorCode.getData();
        if (Objects.nonNull(e.getMessage())) {
            data.setDescription(String.format("%s : %s", data.getDescription(), e.getMessage()));
        }

        data.setLabel(errorCode.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        String errorCodeJson = String.format("{ \"errorCode\": %s}", errorCode.getData().getCode());
        try {
            errorCodeJson = objectMapper.writeValueAsString(errorCode.getData());
        } catch (IOException i) {
            // pass
        }

        return ResponseEntity //
                .status(httpStatus)
                .body(errorCodeJson);
    }

    @ExceptionHandler(ClientBackendException.class)
    public @ResponseBody
    ResponseEntity<Object> handleException(ClientBackendException e) {
        ErrorCode errorCode = e.getErrorCode();
        return doHandleException(errorCode, e);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<Object> handleException(Exception e) {

        // Check if the throwable's message maps to any of our error codes
        //
        Optional<String> matchingException =
                getErrorCodeMapper().keySet().stream().filter(m -> e.getMessage().contains(m)).findAny();

        if (matchingException.isEmpty()) {
            // No match, and in this case, we want to see it!
            //
            log.error("matching exception is empty");
            return doHandleException(ErrorCode.UNKNOWN_SERVER_ERROR, e);
        } else {
            ErrorCode errorCode = getErrorCodeMapper().get(matchingException.get());
            return doHandleException(errorCode, e);
        }

    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleException(ConstraintViolationException ex) {

        log.error("ConstraintViolationException :", ex);

        Map<String, List<String>> errors = ex.getConstraintViolations().stream()
                .collect(groupingBy(
                        constraintViolation -> constraintViolation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private void logException(Exception e) {
        log.error("Handling exception ", e);
    }
}
