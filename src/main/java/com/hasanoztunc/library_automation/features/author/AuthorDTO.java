package com.hasanoztunc.library_automation.features.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long authorId;
    private String name;

    public AuthorDTO(String name) {
        this.name = name;
    }
}
