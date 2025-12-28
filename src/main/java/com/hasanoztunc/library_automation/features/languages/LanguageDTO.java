package com.hasanoztunc.library_automation.features.languages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDTO {
    private String languageId;
    private String name;

    public LanguageDTO(String name) {
        this.name = name;
    }
}
