package com.hasanoztunc.dtos.book;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoBookIU {

    @Size(min = 2, max = 120)
    private String name;

    @Size(min = 2, max = 120)
    private String author;
}
