package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<BookResponseDTO>> createBook(@RequestBody CreateBookDTO createBookDTO) {
        var response = bookService.createBook(createBookDTO);

        return ResponseEntity.ok(response);
    }
}