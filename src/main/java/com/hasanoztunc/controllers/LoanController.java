package com.hasanoztunc.controllers;

import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.dtos.loan.DtoLoan;

public interface LoanController {

    public RootEntity<DtoLoan> borrow(Long bookId);

    public RootEntity<DtoLoan> returnBook(Long bookId);
}
