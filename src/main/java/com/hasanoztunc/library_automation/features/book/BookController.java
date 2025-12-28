package com.hasanoztunc.library_automation.features.book;

import com.hasanoztunc.library_automation.common.constans.PaginationConstants;
import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public ResponseEntity<GenericResponse<BookResponse>> getAllBooks(
            @RequestParam(defaultValue = PaginationConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstants.BOOK_SORT_BY) String sortBy,
            @RequestParam(defaultValue = PaginationConstants.SORT_ORDER) String sortOrder
    ) {
        var response = bookService.getAllBooks(pageNumber, pageSize, sortBy, sortOrder);

        return ResponseEntity.ok(response);
    }
}