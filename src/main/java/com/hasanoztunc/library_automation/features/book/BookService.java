package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;

public interface BookService {

    GenericResponse<BookResponseDTO> createBook(CreateBookDTO createBookDTO);
}