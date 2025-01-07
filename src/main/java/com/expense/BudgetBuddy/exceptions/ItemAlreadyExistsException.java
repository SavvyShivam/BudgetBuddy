package com.expense.BudgetBuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ItemAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 6935533250455599101L;

    public ItemAlreadyExistsException(String message){
        super(message);
    }



}
