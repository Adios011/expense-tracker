package com.muhsener98.expense.tracker.controller;

import com.muhsener98.expense.tracker.controller.exception.InputViolationException;
import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.exception.NoSuchExpenseFoundException;

import java.util.List;

public interface ExpenseController {

    ExpenseResponse addExpense(ExpenseRequest request) throws InputViolationException;
    List<ExpenseResponse> getAll();
    double getTotalSummary();
    double getTotalSummaryByMonth(int month);

    ExpenseResponse delete(int id) throws InputViolationException, NoSuchExpenseFoundException;

    ExpenseResponse updateExpense(ExpenseRequest expenseRequest) throws InputViolationException, NoSuchExpenseFoundException;
}
