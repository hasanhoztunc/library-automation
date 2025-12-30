package com.hasanoztunc.library_automation.features.author;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.book.BookResponse;
import com.hasanoztunc.library_automation.features.book.BookResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(
            AuthorRepository authorRepository,
            ModelMapper modelMapper
    ) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GenericResponse<AuthorDTO> createAuthor(AuthorDTO request) {
        var author = modelMapper.map(request, Author.class);

        var savedAuthor = authorRepository.save(author);
        var authorDTO = modelMapper.map(savedAuthor, AuthorDTO.class);

        return GenericResponse.success(authorDTO);
    }

    @Override
    public GenericResponse<List<AuthorDTO>> getAllAuthors() {
        var authors = authorRepository.findAll();
        var authorDTOs = authors.stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .toList();

        return GenericResponse.success(authorDTOs);
    }

    @Override
    public GenericResponse<AuthorDTO> getAuthorById(Long id) {
        var authorOptional = authorRepository.findById(id);

        if (!authorOptional.isPresent()) {
            return GenericResponse.fail("Author not found");
        }

        var author = authorOptional.get();
        var authorDTO = modelMapper.map(author, AuthorDTO.class);

        return GenericResponse.success(authorDTO);
    }

    @Override
    public GenericResponse<List<AuthorDTO>> searchAuthorsByName(String name) {
        var authors = authorRepository.findByNameContainingIgnoreCase(name);

        var authorDTOs = authors.stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .toList();

        return GenericResponse.success(authorDTOs);
    }

    @Transactional
    @Override
    public GenericResponse<AuthorDTO> updateAuthor(Long id, AuthorDTO request) {
        var authorOptional = authorRepository.findById(id);

        if (!authorOptional.isPresent()) {
            return GenericResponse.fail("Author not found");
        }

        var author = authorOptional.get();
        author.setName(request.getName());

        var updatedAuthor = authorRepository.save(author);
        var authorDTO = modelMapper.map(updatedAuthor, AuthorDTO.class);

        return GenericResponse.success(authorDTO);
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteAuthor(Long id) {
        var authorOptional = authorRepository.findById(id);

        if (!authorOptional.isPresent()) {
            return GenericResponse.fail("Author not found");
        }

        authorRepository.deleteById(id);

        return GenericResponse.empty();
    }

    @Override
    public GenericResponse<BookResponse> getBooksByAuthorId(
            Long authorId,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        var sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        var pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        var pageBooks = authorRepository.findBooksByAuthorId(authorId, pageDetails);

        var bookDTOs = pageBooks.getContent().stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .toList();

        var bookResponse = new BookResponse();
        bookResponse.setBooks(bookDTOs);
        bookResponse.setPageNumber(pageBooks.getNumber());
        bookResponse.setPageSize(pageBooks.getSize());
        bookResponse.setTotalElements(pageBooks.getTotalElements());
        bookResponse.setTotalPages(pageBooks.getTotalPages());
        bookResponse.setLastPage(pageBooks.isLast());

        return GenericResponse.success(bookResponse);
    }
}