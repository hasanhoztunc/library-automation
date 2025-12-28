package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.author.Author;
import com.hasanoztunc.library_automation.features.author.AuthorRepository;
import com.hasanoztunc.library_automation.features.category.Category;
import com.hasanoztunc.library_automation.features.category.CategoryRepository;
import com.hasanoztunc.library_automation.features.languages.LanguageRepository;
import com.hasanoztunc.library_automation.features.publishinghouse.PublishingHouseRepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;
    private final PublishingHouseRepository publishingHouseRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            LanguageRepository languageRepository,
            PublishingHouseRepository publishingHouseRepository,
            ModelMapper modelMapper
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.languageRepository = languageRepository;
        this.publishingHouseRepository = publishingHouseRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GenericResponse<BookResponseDTO> createBook(CreateBookDTO createBookDTO) {
        var book = new Book();

        book.setName(createBookDTO.getName());
        book.setPublishingYear(createBookDTO.getPublishingYear());

        var language = languageRepository.findById(createBookDTO.getLanguageId())
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + createBookDTO.getLanguageId()));
        book.setLanguage(language);

        var publishingHouse = publishingHouseRepository.findById(createBookDTO.getPublishingHouseId())
                .orElseThrow(() -> new RuntimeException("Publishing House not found with id: " + createBookDTO.getPublishingHouseId()));
        book.setPublishingHouse(publishingHouse);

        Set<Author> authors = createBookDTO.getAuthors()
                .stream()
                .map(author -> {
                    return authorRepository.findById(author)
                            .orElseThrow(() -> new RuntimeException("Author not found with id: " + author));
                })
                .collect(Collectors.toSet());
        book.setAuthors(authors);

        Set<Category> categories = createBookDTO.getCategories()
                .stream()
                .map(category -> {
                    return categoryRepository.findById(category)
                            .orElseThrow(() -> new RuntimeException("Category not found with id: " + category));
                })
                .collect(Collectors.toSet());
        book.setCategories(categories);

        var savedBook = bookRepository.save(book);

        var responseDTO = modelMapper.map(savedBook, BookResponseDTO.class);

        return GenericResponse.success(responseDTO);
    }

    @Transactional
    @Override
    public GenericResponse<BookResponse> getAllBooks(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        var sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        var pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Hibernate.initialize(bookRepository.findAll(pageDetails));
        var pageBooks = bookRepository.findAll(pageDetails);

        var books = pageBooks.getContent();

        var booksDTOs = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .toList();

        var bookResponse = new BookResponse(
                booksDTOs,
                pageBooks.getNumber(),
                pageBooks.getSize(),
                pageBooks.getTotalElements(),
                pageBooks.getTotalPages(),
                pageBooks.isLast()
        );

        return GenericResponse.success(bookResponse);
    }

    @Transactional
    @Override
    public GenericResponse<BookResponseDTO> getBookById(Long bookId) {
        var optionalBook = bookRepository.findById(bookId);

        if (!optionalBook.isPresent()) {
            return GenericResponse.fail("Book not found with id: " + bookId);
        }

        var book = optionalBook.get();

        var bookDTO = modelMapper.map(book, BookResponseDTO.class);

        return GenericResponse.success(bookDTO);
    }

    @Transactional
    @Override
    public GenericResponse<BookResponse> searchBooksByName(
            String name,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        var sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        var pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        var pageBooks = bookRepository.findByNameContainingIgnoreCase(name, pageDetails);

        var books = pageBooks.getContent();

        var booksDTOs = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .toList();

        var bookResponse = new BookResponse(
                booksDTOs,
                pageBooks.getNumber(),
                pageBooks.getSize(),
                pageBooks.getTotalElements(),
                pageBooks.getTotalPages(),
                pageBooks.isLast()
        );

        return null;
    }
}