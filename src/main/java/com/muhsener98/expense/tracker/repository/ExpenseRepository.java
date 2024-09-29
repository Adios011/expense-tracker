package com.muhsener98.expense.tracker.repository;

import com.muhsener98.expense.tracker.entity.Expense;

import java.util.List;

public interface ExpenseRepository {

    Expense save(Expense expense);

    Expense findById(int id);
    List<Expense> findAll();

    Expense deleteById(int id);

    Expense update(Expense expenseToBeUpdated);
}
