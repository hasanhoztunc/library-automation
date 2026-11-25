package com.hasanoztunc.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exception<E> {

    private String path;
    private LocalDateTime createTime;
    private E message;
}
