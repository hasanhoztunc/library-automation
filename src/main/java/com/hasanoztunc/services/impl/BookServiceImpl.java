package com.hasanoztunc.services.impl;

import com.hasanoztunc.dtos.book.DtoBook;
import com.hasanoztunc.dtos.book.DtoBookIU;
import com.hasanoztunc.entities.Book;
import com.hasanoztunc.enumerations.MessageType;
import com.hasanoztunc.exceptions.BaseException;
import com.hasanoztunc.exceptions.ErrorMessage;
import com.hasanoztunc.repositories.BookRepository;
import com.hasanoztunc.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public DtoBook save(DtoBookIU dtoBookIU) {
        if (bookRepository.existsByNameAndAuthor(dtoBookIU.getName(), dtoBookIU.getAuthor())) {
            throw new BaseException(
                    new ErrorMessage(
                            MessageType.BOOK_ALREADY_EXISTS,
                            dtoBookIU.getName()
                    )
            );
        }

        Book book = new Book();

        book.setName(dtoBookIU.getName());
        book.setAuthor(dtoBookIU.getAuthor());
        book.setAvailable(true);

        Book savedBook = bookRepository.save(book);

        DtoBook dtoBook = new DtoBook();
        dtoBook.setId(savedBook.getId());
        dtoBook.setName(savedBook.getName());
        dtoBook.setAuthor(savedBook.getAuthor());
        dtoBook.setAvailable(savedBook.isAvailable());
        dtoBook.setCreatedAt(savedBook.getCreatedAt());

        return dtoBook;
    }

    @Override
    public List<DtoBook> getAll() {
        return bookRepository
                .findAll()
                .stream()
                .map(book -> {
                    DtoBook dtoBook = new DtoBook();

                    dtoBook.setId(book.getId());
                    dtoBook.setName(book.getName());
                    dtoBook.setAuthor(book.getAuthor());
                    dtoBook.setAvailable(book.isAvailable());
                    dtoBook.setCreatedAt(book.getCreatedAt());

                    return dtoBook;
                })
                .toList();
    }

    @Override
    public DtoBook getById(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() ->
                        new BaseException(
                                new ErrorMessage(
                                        MessageType.NO_RECORD_EXISTS,
                                        id.toString()
                                )
                        )
                );

        DtoBook dtoBook = new DtoBook();

        dtoBook.setId(book.getId());
        dtoBook.setName(book.getName());
        dtoBook.setAuthor(book.getAuthor());
        dtoBook.setAvailable(book.isAvailable());
        dtoBook.setCreatedAt(book.getCreatedAt());

        return dtoBook;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public DtoBook updateById(Long id, DtoBookIU dtoBookIU) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() ->
                        new BaseException(
                                new ErrorMessage(
                                        MessageType.NO_RECORD_EXISTS,
                                        id.toString()
                                )
                        )
                );

        book.setName(dtoBookIU.getName());
        book.setAuthor(dtoBookIU.getAuthor());

        Book updatedBook = bookRepository.saveAndFlush(book);

        DtoBook dtoBook = new DtoBook();
        dtoBook.setId(updatedBook.getId());
        dtoBook.setName(updatedBook.getName());
        dtoBook.setAuthor(updatedBook.getAuthor());
        dtoBook.setAvailable(updatedBook.isAvailable());
        dtoBook.setCreatedAt(updatedBook.getCreatedAt());

        return dtoBook;
    }
}
