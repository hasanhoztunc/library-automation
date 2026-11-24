package com.hasanoztunc.controllers.impl;

import com.hasanoztunc.controllers.BookController;
import com.hasanoztunc.controllers.impl.base.BaseController;
import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.dtos.book.DtoBook;
import com.hasanoztunc.dtos.book.DtoBookIU;
import com.hasanoztunc.services.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/book")
public class BookControllerImpl extends BaseController implements BookController {

    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoBook> save(@Valid @RequestBody DtoBookIU dtoBookIU) {
        return ok(
                bookService.save(dtoBookIU)
        );
    }

    @GetMapping("/get-all")
    @Override
    public RootEntity<List<DtoBook>> getAll() {
        return ok(
                bookService.getAll()
        );
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoBook> getById(@PathVariable(name = "id", required = true) Long id) {
        return ok(
                bookService.getById(id)
        );
    }


    @DeleteMapping("/{id}")
    @Override
    public RootEntity<Void> deleteById(@PathVariable(name = "id", required = true) Long id) {
        bookService.deleteById(id);
        return ok(null);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoBook> updateById(
            @PathVariable(name = "id", required = true) Long id,
            @Valid @RequestBody DtoBookIU dtoBookIU
    ) {
        return ok(
                bookService.updateById(id, dtoBookIU)
        );
    }
}
