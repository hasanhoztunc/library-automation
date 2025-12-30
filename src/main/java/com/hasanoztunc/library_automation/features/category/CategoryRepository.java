package com.hasanoztunc.library_automation.features.category;

import com.hasanoztunc.library_automation.features.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByNameContainingIgnoreCase(String name);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    Page<Book> findBooksByCategoryId(Long categoryId, Pageable pageable);
}