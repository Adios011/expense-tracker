package com.muhsener98.expense.tracker.service;

import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.entity.Expense;
import com.muhsener98.expense.tracker.exception.InitializationValueException;
import com.muhsener98.expense.tracker.exception.NoSuchExpenseFoundException;
import com.muhsener98.expense.tracker.repository.ExpenseRepository;
import com.muhsener98.expense.tracker.shared.dto.ExpenseDTO;
import com.muhsener98.expense.tracker.shared.mapper.ExpenseMapper;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseIdTracker expenseIDTracker;


    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, ExpenseIdTracker expenseIDTracker) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.expenseIDTracker = expenseIDTracker;
    }

    @Override
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) throws InitializationValueException {
        Expense expenseToBeSaved = expenseMapper.toEntity(expenseDTO);

        int id = expenseIDTracker.getNextId();

        expenseToBeSaved.init(id);

        expenseRepository.save(expenseToBeSaved);

        return expenseMapper.toDto(expenseToBeSaved);

    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();

        return expenseMapper.toResponse(expenses);
    }

    @Override
    public double getTotalSummary() {
        List<Expense> expenses = expenseRepository.findAll();

        double sum = 0 ;
        for (Expense expense: expenses) {
            sum += expense.getAmount();
        }
        return sum;
    }

    @Override
    public double getTotalSummaryByMonth(int month) {
        List<Expense> expenses = expenseRepository.findAll();
        double sum = 0 ;
        for(Expense expense : expenses){
            if(expense.getCreatedAt().getMonth() == month -1  ){
                sum += expense.getAmount();
            }
        }

        return sum;
    }

    @Override
    public ExpenseDTO delete(int id) throws NoSuchExpenseFoundException{
        Expense deletedExpense = expenseRepository.deleteById(id);
        if(deletedExpense == null)
            throw new NoSuchExpenseFoundException(id);

        return expenseMapper.toDto(deletedExpense);
    }

    @Override
    public ExpenseDTO update(ExpenseDTO expenseDTO) throws NoSuchExpenseFoundException {
        Expense expenseToBeUpdated = expenseMapper.toEntity(expenseDTO);

        Expense expenseUpdated = expenseRepository.update(expenseToBeUpdated );

        if(expenseUpdated == null)
            throw new NoSuchExpenseFoundException(expenseDTO.getId());
        else
            return expenseMapper.toDto(expenseUpdated);
    }
}
