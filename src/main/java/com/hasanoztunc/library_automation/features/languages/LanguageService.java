package com.hasanoztunc.library_automation.features.languages;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.book.BookResponse;

import java.util.List;

public interface LanguageService {

    GenericResponse<LanguageDTO> createLanguage(LanguageDTO request);

    GenericResponse<List<LanguageDTO>> getAllLanguages();

    GenericResponse<LanguageDTO> getLanguageById(Long id);

    GenericResponse<List<LanguageDTO>> searchLanguagesByName(String name);

    GenericResponse<LanguageDTO> updateLanguage(Long id, LanguageDTO request);

    GenericResponse<Void> deleteLanguage(Long id);

    GenericResponse<BookResponse> getBooksByLanguageId(
            Long languageId,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    );
}