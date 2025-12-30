package com.hasanoztunc.library_automation.features.author;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.book.BookResponse;
import com.hasanoztunc.library_automation.features.book.BookResponseDTO;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    GenericResponse<AuthorDTO> createAuthor(AuthorDTO request);

    GenericResponse<List<AuthorDTO>> getAllAuthors();

    GenericResponse<AuthorDTO> getAuthorById(Long id);

    GenericResponse<List<AuthorDTO>> searchAuthorsByName(String name);

    GenericResponse<AuthorDTO> updateAuthor(Long id, AuthorDTO request);

    GenericResponse<Void> deleteAuthor(Long id);

    GenericResponse<BookResponse> getBooksByAuthorId(
            Long authorId,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    );
}