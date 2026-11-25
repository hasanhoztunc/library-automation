package com.hasanoztunc.services;

import com.hasanoztunc.dtos.loan.DtoLoan;

public interface LoanService {

    public DtoLoan borrow(Long bookId);

    public DtoLoan returnBook(Long bookId);
}
