package com.hasanoztunc.dtos.loan;

import com.hasanoztunc.dtos.book.DtoBook;
import com.hasanoztunc.dtos.user.DtoUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLoan {

    private DtoBook book;
    private DtoUser user;
    private LocalDateTime borrowedAt;
    private LocalDateTime dueAt;
}
