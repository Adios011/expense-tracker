package com.muhsener98.expense.tracker.cli.requestHandlers;

import com.muhsener98.expense.tracker.cli.OptionContainer;
import com.muhsener98.expense.tracker.controller.ExpenseController;
import com.muhsener98.expense.tracker.controller.exception.InputViolationException;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.exception.NoSuchExpenseFoundException;
import org.apache.commons.cli.CommandLine;


public class DeleteRequestHandler {

    private final ExpenseController expenseController;

    public DeleteRequestHandler(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    public void handle(CommandLine commandLine , OptionContainer optionContainer){
        String idString = commandLine.getOptionValue("id");
        checkMandatoryOptionsExist(optionContainer,idString);
        int id = checkAndConvertIdInput(idString);

        try{
            ExpenseResponse deletedExpense = expenseController.delete(id);
            System.out.println("Expense deleted successfully (ID: " + deletedExpense.getId() + ")");
        }catch (InputViolationException e ){
            System.err.println(e.getMessage());
            System.exit(-1);
        }catch (NoSuchExpenseFoundException e){
            System.err.println("No such expense found: " + e.getId());
            System.exit(0);
        }
        catch (Exception exception){
            System.err.println("Unknown internal error!");
            System.exit(-1);
        }
    }


    private int checkAndConvertIdInput(String idString){
        int id = 0 ;
        try {
            id = Integer.parseInt(idString);

        }catch (NumberFormatException e){
            System.err.println("wrong id format");
            System.exit(-1);
        }

        return id;
    }

    private void checkMandatoryOptionsExist(OptionContainer optionContainer , String idString){
        if (idString == null){
            System.out.println("missing option: id");
            optionContainer.printHelp("delete");
            System.exit(-1);
        }
    }
}
