package com.muhsener98.expense.tracker.exception;

public class ExpenseAlreadyInitializedException extends RuntimeException{

    public ExpenseAlreadyInitializedException(String message) {
        super(message);
    }
}
