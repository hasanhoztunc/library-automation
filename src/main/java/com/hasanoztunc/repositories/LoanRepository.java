package com.hasanoztunc.repositories;

import com.hasanoztunc.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);

    long countByUserIdAndReturnedAtIsNull(Long userId);

    Optional<Loan> findByBookIdAndReturnedAtIsNull(Long bookId);
}
