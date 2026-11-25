package com.hasanoztunc.services;

import com.hasanoztunc.dtos.book.DtoBook;
import com.hasanoztunc.dtos.book.DtoBookIU;

import java.util.List;

public interface BookService {

    DtoBook save(DtoBookIU dtoBookIU);

    List<DtoBook> getAll();

    DtoBook getById(Long id);

    void deleteById(Long id);

    DtoBook updateById(Long id, DtoBookIU dtoBookIU);
}
