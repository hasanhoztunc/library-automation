package com.hasanoztunc.library_automation.features.publishinghouse;

import com.hasanoztunc.library_automation.features.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {

    List<PublishingHouse> findByNameContainingIgnoreCase(String name);

    @Query("SELECT b FROM Book b WHERE b.publishingHouse.id = :publishingHouseId")
    Page<Book> findBooksByPublishingHouseId(Long publishingHouseId, Pageable pageable);
}