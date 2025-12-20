package com.hasanoztunc.library_automation.features.author;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;

import java.util.List;

public interface AuthorService {

    GenericResponse<AuthorDTO> createAuthor(AuthorDTO request);

    GenericResponse<List<AuthorDTO>> getAllAuthors();

    GenericResponse<AuthorDTO> getAuthorById(Long id);

    GenericResponse<List<AuthorDTO>> searchAuthorsByName(String name);

    GenericResponse<AuthorDTO> updateAuthor(Long id, AuthorDTO request);

    GenericResponse<Void> deleteAuthor(Long id);
}