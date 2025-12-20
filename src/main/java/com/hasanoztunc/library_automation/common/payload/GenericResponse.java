package com.hasanoztunc.library_automation.common.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenericResponse<TModel> {
    private Boolean success;
    private String message;
    private TModel data;

    public static <TModel> GenericResponse<TModel> empty() {
        return success(null);
    }

    public static <TModel> GenericResponse<TModel> success(TModel data) {
        return GenericResponse.<TModel>builder()
                .success(true)
                .message("Success")
                .data(data)
                .build();
    }

    public static <TModel> GenericResponse<TModel> fail(String message) {
        return GenericResponse.<TModel>builder()
                .success(false)
                .message(message)
                .build();
    }
}
