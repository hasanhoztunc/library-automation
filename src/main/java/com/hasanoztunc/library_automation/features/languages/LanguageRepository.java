package com.hasanoztunc.library_automation.features.languages;

import com.hasanoztunc.library_automation.features.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findByNameContainingIgnoreCase(String name);

    @Query("SELECT b FROM Book b WHERE b.language.id = :languageId")
    Page<Book> findBooksByLanguageId(Long languageId, Pageable pageable);
}