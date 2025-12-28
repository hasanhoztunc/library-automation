package com.hasanoztunc.library_automation.configurations;

import com.hasanoztunc.library_automation.features.authentication.AuthenticationService;
import com.hasanoztunc.library_automation.features.authentication.LoginDTO;
import com.hasanoztunc.library_automation.features.authentication.RegisterDTO;
import com.hasanoztunc.library_automation.features.author.AuthorDTO;
import com.hasanoztunc.library_automation.features.author.AuthorService;
import com.hasanoztunc.library_automation.features.book.BookService;
import com.hasanoztunc.library_automation.features.book.CreateBookDTO;
import com.hasanoztunc.library_automation.features.category.CategoryDTO;
import com.hasanoztunc.library_automation.features.category.CategoryService;
import com.hasanoztunc.library_automation.features.languages.LanguageDTO;
import com.hasanoztunc.library_automation.features.languages.LanguageService;
import com.hasanoztunc.library_automation.features.publishinghouse.PublishingHouseDTO;
import com.hasanoztunc.library_automation.features.publishinghouse.PublishingHouseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppMocks {

    private final AuthenticationService authenticationService;
    private final LanguageService languageService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final PublishingHouseService publishingHouseService;
    private final BookService bookService;

    public AppMocks(
            AuthenticationService authenticationService,
            LanguageService languageService,
            CategoryService categoryService,
            AuthorService authorService,
            PublishingHouseService publishingHouseService,
            BookService bookService
    ) {
        this.authenticationService = authenticationService;
        this.languageService = languageService;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.publishingHouseService = publishingHouseService;
        this.bookService = bookService;
    }

    @Bean
    public CommandLineRunner mockDataInitializer() {
        return args -> {
            var member = new RegisterDTO(
                    "hasanoztunc",
                    "Hasan Oztunc",
                    "hasanoztunc@gmail.com",
                    "password123"
            );
            authenticationService.registerUser(member);

            var loginMember = new LoginDTO(
                    "hasanoztunc",
                    "password123"
            );
            authenticationService.loginUser(loginMember);

            var language = new LanguageDTO(
                "Turkish"
            );
            languageService.createLanguage(language);

            var category = new CategoryDTO(
                "Science Fiction"
            );
            categoryService.createCategory(category);

            var author = new AuthorDTO(
                    "Isaac Asimov"
            );
            authorService.createAuthor(author);

            var publishingHouse = new PublishingHouseDTO(
                    "Penguin Random House"
            );
            publishingHouseService.createPublishingHouse(publishingHouse);

            var book = new CreateBookDTO(
                    "Foundation",
                    1951,
                    1L,
                    1L,
                    List.of(1L),
                    List.of(1L)
            );
            bookService.createBook(book);
        };
    }
}