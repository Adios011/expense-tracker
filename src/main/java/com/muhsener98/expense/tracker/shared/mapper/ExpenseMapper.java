package com.muhsener98.expense.tracker.shared.mapper;

import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.entity.Expense;
import com.muhsener98.expense.tracker.shared.dto.ExpenseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapper {

    public ExpenseDTO toDto(ExpenseRequest request) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setAmount(request.getAmount());
        dto.setDescription(request.getDescription());
        dto.setId(request.getId());

        return dto;
    }

    public ExpenseDTO toDto(Expense entity) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public Expense toEntity(ExpenseDTO dto) {
        return Expense.Builder.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    public ExpenseResponse toResponse(ExpenseDTO dto) {
        return new ExpenseResponse(dto.getId(), dto.getDescription(), dto.getAmount(), dto.getCreatedAt());
    }

    public ExpenseResponse toResponse(Expense expense){
        return new ExpenseResponse(expense.getId() , expense.getDescription() , expense.getAmount() , expense.getCreatedAt());
    }

    public List<ExpenseResponse> toResponse(List<Expense> expenseList){
        return expenseList.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
