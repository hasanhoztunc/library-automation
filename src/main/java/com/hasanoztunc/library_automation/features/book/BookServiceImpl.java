package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.author.Author;
import com.hasanoztunc.library_automation.features.author.AuthorDTO;
import com.hasanoztunc.library_automation.features.author.AuthorRepository;
import com.hasanoztunc.library_automation.features.category.Category;
import com.hasanoztunc.library_automation.features.category.CategoryDTO;
import com.hasanoztunc.library_automation.features.category.CategoryRepository;
import com.hasanoztunc.library_automation.features.languages.LanguageDTO;
import com.hasanoztunc.library_automation.features.languages.LanguageRepository;
import com.hasanoztunc.library_automation.features.publishinghouse.PublishingHouseDTO;
import com.hasanoztunc.library_automation.features.publishinghouse.PublishingHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

        var responseDTO = new BookResponseDTO();
        responseDTO.setName(savedBook.getName());
        responseDTO.setPublishingYear(savedBook.getPublishingYear());
        responseDTO.setLanguage(modelMapper.map(savedBook.getLanguage(), LanguageDTO.class));
        responseDTO.setPublishingHouse(modelMapper.map(savedBook.getPublishingHouse(), PublishingHouseDTO.class));

        var authorsDTO = savedBook.getAuthors()
                .stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .toList();
        responseDTO.setAuthors(authorsDTO);

        var categoriesDTO = savedBook.getCategories()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        responseDTO.setCategories(categoriesDTO);

        return GenericResponse.success(responseDTO);
    }
}