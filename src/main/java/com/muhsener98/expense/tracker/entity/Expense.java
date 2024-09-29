package com.muhsener98.expense.tracker.entity;

import com.muhsener98.expense.tracker.exception.ExpenseAlreadyInitializedException;
import com.muhsener98.expense.tracker.exception.ExpenseDomainException;
import com.muhsener98.expense.tracker.exception.InitializationValueException;
import com.muhsener98.expense.tracker.exception.ValidationErrorMessages;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

public class Expense implements Serializable {

    private Integer id;
    private String description;
    private Double amount;
    private Date createdAt;

    private Expense() {

    }


    public Expense(String description, Double amount) throws ExpenseDomainException {
        this.description = description;
        this.amount = amount;
    }

    private Expense(Builder builder) {
        id = builder.id;
        description = builder.description;
        amount = builder.amount;
        createdAt = builder.createdAt;
    }


    /**
     * @param id unique identifier
     * @throws InitializationValueException       if initial values  are invalid.
     * @throws ExpenseAlreadyInitializedException If ID is not null.
     */
    public void init(Integer id) throws InitializationValueException, ExpenseAlreadyInitializedException {
        if (this.id != null && this.id != 0)
            throw new ExpenseAlreadyInitializedException("id " + this.id);

        validateFields();
        this.id = id;
        createdAt = new Date();
    }


    private void validateFields() throws InitializationValueException {
        StringJoiner failureMessage = new StringJoiner("--");
        if (description == null || description.isEmpty() || description.isBlank())
            failureMessage.add(ValidationErrorMessages.INVALID_EXPENSE_DESCRIPTION);

        if (amount <= 0)
            failureMessage.add(ValidationErrorMessages.INVALID_EXPENSE_AMOUNT);


        if (failureMessage.length() != 0)
            throw new InitializationValueException(failureMessage.toString());


    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public static final class Builder {
        private Integer id;
        private String description;
        private Double amount;
        private Date createdAt;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder amount(Double val) {
            amount = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Expense build() {
            return new Expense(this);
        }
    }
}
