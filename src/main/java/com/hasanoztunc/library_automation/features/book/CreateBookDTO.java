package com.hasanoztunc.library_automation.features.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {
    private String name;
    private Integer publishingYear;
    private Long languageId;
    private Long publishingHouseId;
    private List<Long> authors;
    private List<Long> categories;
}