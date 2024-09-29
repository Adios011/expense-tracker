package com.muhsener98.expense.tracker.controller.model;

import java.util.Date;

public class ExpenseResponse {

    private int id ;
    private String description;
    private double amount ;
    private Date createdAt;


    public ExpenseResponse(int id, String description, double amount, Date createdAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
