package com.hasanoztunc.controllers.impl;

import com.hasanoztunc.controllers.LoanController;
import com.hasanoztunc.controllers.impl.base.BaseController;
import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.dtos.loan.DtoLoan;
import com.hasanoztunc.services.LoanService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/book")
public class LoanControllerImpl extends BaseController implements LoanController {

    private final LoanService loanService;

    public LoanControllerImpl(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/{id}/borrow")
    @Override
    public RootEntity<DtoLoan> borrow(@PathVariable(name = "id", required = true) Long bookId) {
        return ok(
                loanService.borrow(bookId)
        );
    }

    @PostMapping("/{id}/return")
    @Override
    public RootEntity<DtoLoan> returnBook(@PathVariable(name = "id", required = true) Long bookId) {
        return ok(
                loanService.returnBook(bookId)
        );
    }
}
