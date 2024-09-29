package com.muhsener98.expense.tracker.cli.requestHandlers;

import com.muhsener98.expense.tracker.cli.OptionContainer;
import com.muhsener98.expense.tracker.controller.exception.InputViolationException;
import com.muhsener98.expense.tracker.controller.ExpenseController;
import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import org.apache.commons.cli.CommandLine;

public class AddRequestHandler {

    private final ExpenseController expenseController;

    public AddRequestHandler(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    public void handle(CommandLine commandLine, OptionContainer optionContainer) {
        String description = commandLine.getOptionValue("description");
        String stringAmount = commandLine.getOptionValue("amount");

        checkOptionsExists(optionContainer, description, stringAmount);
        double amount = checkAmountFormatAndConvert(stringAmount);

        ExpenseRequest expenseRequest = new ExpenseRequest(description, amount);

        ExpenseResponse expenseResponse = null;
        try {
            expenseResponse = expenseController.addExpense(expenseRequest);
            System.out.printf("Expense added successfully (ID:%s)%n", expenseResponse.getId());
        } catch (InputViolationException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.err.println("unknown internal error!");
            System.exit(0);
        }


    }

    private double checkAmountFormatAndConvert(String stringAmount) {
        double amount = 0;
        try {
            amount = Double.parseDouble(stringAmount);
        } catch (NumberFormatException numberFormatException) {
            System.err.println("wrong amount format!");
            System.exit(-1);
        }

        return amount;


    }

    private void checkOptionsExists(OptionContainer optionContainer, String description, String amount) {

        if (description == null) {
            System.out.println("missing option:  description");
            optionContainer.printHelp("add");
            System.exit(-1);
        }

        if (amount == null) {
            System.out.println("missing option:  amount");
            optionContainer.printHelp("add");
            System.exit(-1);
        }
    }
}
