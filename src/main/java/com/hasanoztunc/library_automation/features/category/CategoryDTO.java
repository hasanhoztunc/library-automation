package com.hasanoztunc.library_automation.features.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }
}