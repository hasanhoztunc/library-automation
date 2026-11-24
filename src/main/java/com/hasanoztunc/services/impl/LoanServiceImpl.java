package com.hasanoztunc.services.impl;

import com.hasanoztunc.configurations.properties.ApplicationProperties;
import com.hasanoztunc.controllers.impl.base.BaseController;
import com.hasanoztunc.dtos.book.DtoBook;
import com.hasanoztunc.dtos.loan.DtoLoan;
import com.hasanoztunc.dtos.user.DtoUser;
import com.hasanoztunc.entities.Book;
import com.hasanoztunc.entities.Loan;
import com.hasanoztunc.enumerations.MessageType;
import com.hasanoztunc.exceptions.BaseException;
import com.hasanoztunc.exceptions.ErrorMessage;
import com.hasanoztunc.repositories.BookRepository;
import com.hasanoztunc.repositories.LoanRepository;
import com.hasanoztunc.repositories.UserRepository;
import com.hasanoztunc.security.entities.User;
import com.hasanoztunc.services.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanServiceImpl extends BaseController implements LoanService {

    private final ApplicationProperties applicationProperties;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanServiceImpl(
            ApplicationProperties applicationProperties,
            BookRepository bookRepository,
            LoanRepository loanRepository,
            UserRepository userRepository
    ) {
        this.applicationProperties = applicationProperties;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public DtoLoan borrow(Long bookId) {
        User user = getCurrentUser();

        long activeLoans = loanRepository.countByUserIdAndReturnedAtIsNull(user.getId());

        if (activeLoans >= applicationProperties.getMaxLoanCount()) {
            throw new BaseException(
                    new ErrorMessage(MessageType.LOAN_LIMIT_EXCEEDED, null)
            );
        }

        Book book = bookRepository.findByIdForUpdate(bookId).orElseThrow(() ->
                new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXISTS, bookId.toString())
                )
        );

        if (loanRepository.existsByBookIdAndReturnedAtIsNull(bookId)) {
            throw new BaseException(
                    new ErrorMessage(MessageType.BOOK_ALREADY_BORROWED, null)
            );
        }

        LocalDateTime now = LocalDateTime.now();

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setBorrowedAt(now);
        loan.setDueAt(now.plusDays(14));

        Loan savedLoan = loanRepository.saveAndFlush(loan);

        return convertToDtoLoan(savedLoan);
    }

    @Transactional
    @Override
    public DtoLoan returnBook(Long bookId) {
        Loan loan = loanRepository.findByBookIdAndReturnedAtIsNull(bookId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.LOAN_NOT_FOUND, bookId.toString())));

        loan.setReturnedAt(LocalDateTime.now());

        return convertToDtoLoan(loanRepository.saveAndFlush(loan));
    }

    private DtoLoan convertToDtoLoan(Loan loan) {
        DtoLoan dtoLoan = new DtoLoan();

        dtoLoan.setBorrowedAt(loan.getBorrowedAt());
        dtoLoan.setDueAt(loan.getDueAt());

        DtoUser dtoUser = new DtoUser();
        dtoUser.setUsername(loan.getUser().getUsername());
        dtoLoan.setUser(dtoUser);

        DtoBook dtoBook = new DtoBook();
        dtoBook.setId(loan.getBook().getId());
        dtoBook.setName(loan.getBook().getName());
        dtoBook.setAuthor(loan.getBook().getAuthor());
        dtoBook.setCreatedAt(loan.getBook().getCreatedAt());
        dtoLoan.setBook(dtoBook);

        return dtoLoan;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null &&
                !authentication.isAuthenticated() &&
                (authentication instanceof AnonymousAuthenticationToken)) {

            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, null)
            );
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username).orElseThrow(() ->
                    new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, null)
                )
        );
    }
}
