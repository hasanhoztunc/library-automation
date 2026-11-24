package com.hasanoztunc.dtos.book;

import com.hasanoztunc.dtos.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoBook extends BaseDto {

    private String name;
    private String author;
    private boolean isAvailable;
}
