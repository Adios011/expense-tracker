package com.muhsener98.expense.tracker.controller.model;

public class ExpenseRequest {

    private int id ;
    private String description;
    private double amount ;

    public String getDescription() {
        return description;
    }

    public ExpenseRequest(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public ExpenseRequest(int id, String description, double amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }
}
