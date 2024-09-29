package com.muhsener98.expense.tracker.cli;

public enum CommandNames {
    ADD("add"),
    LIST("list"),
    SUMMARY("summary"),
    DELETE("delete"),
    UPDATE("update");


    private String val;

    CommandNames(String val){
        this.val = val;
    }


    public String toString(){
        return val;
    }

    public String getVal(){
        return toString();
    }
}
