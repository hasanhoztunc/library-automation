package com.hasanoztunc.controllers;

import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.dtos.book.DtoBook;
import com.hasanoztunc.dtos.book.DtoBookIU;

import java.util.List;

public interface BookController {

    RootEntity<DtoBook> save(DtoBookIU dtoBookIU);

    RootEntity<List<DtoBook>> getAll();

    RootEntity<DtoBook> getById(Long id);

    RootEntity<Void> deleteById(Long id);

    RootEntity<DtoBook> updateById(Long id, DtoBookIU dtoBookIU);
}
