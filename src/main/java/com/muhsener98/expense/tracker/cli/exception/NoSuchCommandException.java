package com.muhsener98.expense.tracker.cli.exception;

public class NoSuchCommandException extends Exception{

    public NoSuchCommandException(String command) {
        super("No such command found: " + command);
    }
}
