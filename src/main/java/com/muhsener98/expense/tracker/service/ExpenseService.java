package com.muhsener98.expense.tracker.service;

import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.entity.Expense;
import com.muhsener98.expense.tracker.exception.InitializationValueException;
import com.muhsener98.expense.tracker.exception.NoSuchExpenseFoundException;
import com.muhsener98.expense.tracker.shared.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {

     ExpenseDTO addExpense(ExpenseDTO expenseDTO) throws InitializationValueException;

    List<ExpenseResponse> getAllExpenses();

    double getTotalSummary();

    double getTotalSummaryByMonth(int month);

    ExpenseDTO delete(int id) throws NoSuchExpenseFoundException;

    ExpenseDTO update(ExpenseDTO expenseDTO) throws NoSuchExpenseFoundException;
}
