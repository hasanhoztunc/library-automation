package com.hasanoztunc.dtos.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RootEntity<T> {

    private int status;
    private T payload;
    private String errorMessage;

    public static <T> RootEntity<T> ok(T payload) {
        RootEntity<T> rootEntity = new RootEntity<>();

        rootEntity.setErrorMessage(null);
        rootEntity.setStatus(HttpStatus.OK.value());
        rootEntity.setPayload(payload);

        return rootEntity;
    }

    public static <T> RootEntity<T> error(String errorMessage) {
        RootEntity<T> rootEntity = new RootEntity<>();

        rootEntity.setErrorMessage(errorMessage);
        rootEntity.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        rootEntity.setPayload(null);

        return rootEntity;
    }
}
