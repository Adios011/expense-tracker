package com.muhsener98.expense.tracker.controller;

import com.muhsener98.expense.tracker.controller.exception.InputViolationException;
import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;

public class InputValidator {

    public static void validate(ExpenseRequest expenseRequest) throws InputViolationException {
        String description = expenseRequest.getDescription();
        double amount = expenseRequest.getAmount();
        if(description.isEmpty() || description.isBlank()){
            throw new InputViolationException("description cannot be empty");
        }

        if(amount <= 0 )
            throw new InputViolationException("amount must be greater than zero");
    }

    public static void validateUpdateRequest(ExpenseRequest request) throws InputViolationException {
        validate(request);

        int id = request.getId();

        if(id <= 0)
            throw new InputViolationException("id must be greater than zero");

    }


    public static void validateExpenseId(int id) throws InputViolationException{
        if(id <= 0 )
            throw new InputViolationException("ID must be greater than 0");
    }
}
