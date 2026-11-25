package com.hasanoztunc.handlers;

import com.hasanoztunc.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // region BaseException

    @ExceptionHandler(value = { BaseException.class })
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException exception, WebRequest webRequest) {
        return ResponseEntity
                .badRequest()
                .body(createApiError(exception.getMessage(), webRequest, HttpStatus.BAD_REQUEST));
    }

    // endregion

    // region MethodArguement Exception

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            WebRequest webRequest
    ) {
        Map<String, List<String>> map = new HashMap<>();

        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objectError).getField();

            if (map.containsKey(fieldName)) {
                map.put(fieldName, addValue(map.get(fieldName), objectError.getDefaultMessage()));
            } else {
                map.put(fieldName, addValue(new ArrayList<>(), objectError.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(map, webRequest, HttpStatus.BAD_REQUEST));
    }

    // region Helpers

    private <E> ApiError<E> createApiError(E message, WebRequest webRequest, HttpStatus status) {
        ApiError<E> apiError = new ApiError<>();

        apiError.setStatus(status.value());

        Exception<E> exception = new Exception<>();
        exception.setCreateTime(LocalDateTime.now());
        exception.setMessage(message);
        exception.setPath(webRequest.getDescription(false));

        apiError.setException(exception);

        return apiError;
    }

    private List<String> addValue(List<String> list, String newValue) {
        list.add(newValue);

        return list;
    }

    // endregion
}
