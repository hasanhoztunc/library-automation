package com.hasanoztunc.repositories;

import com.hasanoztunc.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByNameAndAuthor(String name, String author);
}
