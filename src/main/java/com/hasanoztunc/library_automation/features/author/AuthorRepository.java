package com.hasanoztunc.library_automation.features.author;

import com.hasanoztunc.library_automation.features.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByNameContainingIgnoreCase(String name);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    Page<Book> findBooksByAuthorId(Long authorId, Pageable pageable);
}