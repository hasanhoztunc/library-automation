package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;

import java.util.List;

public interface BookService {

    GenericResponse<BookResponseDTO> createBook(CreateBookDTO createBookDTO);

    GenericResponse<BookResponse> getAllBooks(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    );

    GenericResponse<BookResponseDTO> getBookById(Long bookId);

    GenericResponse<BookResponse> searchBooksByName(
            String name,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    );
}