package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.features.author.AuthorDTO;
import com.hasanoztunc.library_automation.features.category.CategoryDTO;
import com.hasanoztunc.library_automation.features.languages.LanguageDTO;
import com.hasanoztunc.library_automation.features.publishinghouse.PublishingHouseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private String name;
    private Integer publishingYear;
    private LanguageDTO language;
    private PublishingHouseDTO publishingHouse;
    private List<AuthorDTO> authors;
    private List<CategoryDTO> categories;
}
