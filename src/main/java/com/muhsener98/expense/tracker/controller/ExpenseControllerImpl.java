package com.muhsener98.expense.tracker.controller;

import com.muhsener98.expense.tracker.controller.exception.InputViolationException;
import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.exception.InitializationValueException;
import com.muhsener98.expense.tracker.exception.NoSuchExpenseFoundException;
import com.muhsener98.expense.tracker.service.ExpenseService;
import com.muhsener98.expense.tracker.shared.dto.ExpenseDTO;
import com.muhsener98.expense.tracker.shared.mapper.ExpenseMapper;

import java.util.List;

public class ExpenseControllerImpl implements ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;


    public ExpenseControllerImpl(ExpenseService expenseService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
    }

    public ExpenseResponse addExpense(ExpenseRequest request) throws InputViolationException {
        InputValidator.validate(request);
        ExpenseDTO expenseDTO = expenseMapper.toDto(request);

        ExpenseDTO savedExpense = null;
        try {
            savedExpense = expenseService.addExpense(expenseDTO);
        } catch (InitializationValueException e) {
            throw new InputViolationException(e.getMessage());
        }

        return expenseMapper.toResponse(savedExpense);
    }

    @Override
    public List<ExpenseResponse> getAll() {
        return expenseService.getAllExpenses();
    }

    @Override
    public double getTotalSummary() {
        return expenseService.getTotalSummary();
    }

    @Override
    public double getTotalSummaryByMonth(int month) {
        return expenseService.getTotalSummaryByMonth(month);
    }

    @Override
    public ExpenseResponse delete(int id) throws InputViolationException, NoSuchExpenseFoundException {
        InputValidator.validateExpenseId(id);

        return expenseMapper.toResponse(expenseService.delete(id));
    }

    @Override
    public ExpenseResponse updateExpense(ExpenseRequest expenseRequest) throws InputViolationException, NoSuchExpenseFoundException {
        InputValidator.validateUpdateRequest(expenseRequest);

        ExpenseDTO expenseToBeUpdated = expenseMapper.toDto(expenseRequest);




        return expenseMapper.toResponse(expenseService.update(expenseToBeUpdated));
    }
}
