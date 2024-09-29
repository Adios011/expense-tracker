package com.muhsener98.expense.tracker.cli.requestHandlers;

import com.muhsener98.expense.tracker.cli.ApplicationCLI;
import com.muhsener98.expense.tracker.cli.OptionContainer;
import com.muhsener98.expense.tracker.controller.ExpenseController;
import com.muhsener98.expense.tracker.controller.exception.InputViolationException;
import com.muhsener98.expense.tracker.controller.model.ExpenseRequest;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.exception.NoSuchExpenseFoundException;
import org.apache.commons.cli.CommandLine;

public class UpdateRequestHandler {

    private final ExpenseController expenseController;

    public UpdateRequestHandler(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    public void handle(CommandLine commandLine , OptionContainer optionContainer){
        String idString = commandLine.getOptionValue("id");
        String description = commandLine.getOptionValue("description");
        String amountString = commandLine.getOptionValue("amount");
        checkMandatoryOptionsExist(optionContainer ,idString, description , amountString);

        int id = checkAndConvertInputId(idString);
        double amount = checkAndConvertInputAmount(amountString);

        ExpenseRequest expenseRequest = new ExpenseRequest(id, description , amount);

        try{
            ExpenseResponse expenseResponse = expenseController.updateExpense(expenseRequest);
            System.out.println("Expense updated successfully (ID: " + expenseResponse.getId()+")");
        }catch (InputViolationException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }catch (NoSuchExpenseFoundException e){
            System.err.println("No such expense found " + e.getId());
            System.exit(-1);
        }


    }

    private void checkMandatoryOptionsExist(OptionContainer optionContainer ,String idString,  String description, String amountString){
        if(description == null){
            System.out.println("Missing option: description" );
            optionContainer.printHelp("update");
            System.exit(-1);
        }else if(amountString == null){
            System.out.println("Missing option: amount");
            optionContainer.printHelp("update");
            System.exit(-1);
        }else if(idString == null){
            System.out.println("Missing option: id");
            optionContainer.printHelp("update");
            System.exit(-1);
        }
    }

    private int checkAndConvertInputId(String idString){
        try{
            return Integer.parseInt(idString);
        }catch (NumberFormatException e){
            System.out.println("wrong id format");
            System.exit(-1);
        }

        return  0;
    }

    private double checkAndConvertInputAmount(String amountString){
        try{
            return Double.parseDouble(amountString);
        }catch (NumberFormatException e){
            System.out.println("wrong amount format");
            System.exit(-1);
        }
        return  0;
    }
}
