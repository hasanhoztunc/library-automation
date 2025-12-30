package com.hasanoztunc.library_automation.features.author;

import com.hasanoztunc.library_automation.common.constans.PaginationConstants;
import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.book.BookResponse;
import com.hasanoztunc.library_automation.features.book.BookResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<AuthorDTO>> createAuthor(@RequestBody AuthorDTO request) {
        var response = authorService.createAuthor(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<GenericResponse<List<AuthorDTO>>> getAllAuthors() {
        var response = authorService.getAllAuthors();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<AuthorDTO>> getAuthorById(@PathVariable Long id) {
        var response = authorService.getAuthorById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<GenericResponse<BookResponse>> getBooksByAuthorId(
            @PathVariable Long id,
            @RequestParam(defaultValue = PaginationConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstants.BOOK_SORT_BY) String sortBy,
            @RequestParam(defaultValue = PaginationConstants.SORT_ORDER) String sortOrder
    ) {
        var response = authorService.getBooksByAuthorId(id, pageNumber, pageSize, sortBy, sortOrder);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse<List<AuthorDTO>>> searchAuthorsByName(@RequestParam String name) {
        var response = authorService.searchAuthorsByName(name);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<AuthorDTO>> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO request) {
        var response = authorService.updateAuthor(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteAuthor(@PathVariable Long id) {
        var response = authorService.deleteAuthor(id);

        return ResponseEntity.ok(response);
    }
}