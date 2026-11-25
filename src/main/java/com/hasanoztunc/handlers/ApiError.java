package com.hasanoztunc.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<E> {

    private int status;
    private Exception<E> exception;
}
