package com.muhsener98.expense.tracker.exception;

public class NoSuchExpenseFoundException extends Exception{

    private final int id ;

    public NoSuchExpenseFoundException(int id) {
        super(String.valueOf(id));
        this.id = id ;
    }

    public int getId(){
        return id;
    }


}
