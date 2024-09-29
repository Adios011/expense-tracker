package com.muhsener98.expense.tracker.cli.requestHandlers;

import com.muhsener98.expense.tracker.cli.OptionContainer;
import com.muhsener98.expense.tracker.controller.ExpenseController;
import com.muhsener98.expense.tracker.controller.model.ExpenseResponse;
import com.muhsener98.expense.tracker.entity.Expense;
import org.apache.commons.cli.CommandLine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListRequestHandler {

    private final String DATE_FORMAT = "dd-MM-yyyy";

    private final ExpenseController expenseController;

    public ListRequestHandler(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    public void handle(CommandLine commandLine, OptionContainer optionContainer) {
        List<ExpenseResponse> responseList = expenseController.getAll();

        printView(responseList);


    }

    private void printView(List<ExpenseResponse> expenses) {
        StringBuilder sb = new StringBuilder();

        int maxIdLength = maxIdLength(expenses);
        int maxDescriptionLength = maxDescriptionLength(expenses);
        int maxAmountLength = maxAmountLength(expenses);



        System.out.println("#  " + String.format("%-" + maxIdLength + "s  " , "ID") +
                String.format("%-10s  " ,"Date") +
                String.format("%-" + maxDescriptionLength +"s  " , "Description") +
                String.format("%-" + maxAmountLength + "s " , "Amount"));

        for(ExpenseResponse expense : expenses){
            sb.append("#  ");
            sb.append(String.format("%-" + maxIdLength + "s  " , expense.getId()));
            sb.append(String.format("%-10s  " , formatDate(expense.getCreatedAt())));
            sb.append(String.format("%-" + maxDescriptionLength + "s  " , expense.getDescription()));
            sb.append(String.format("%-" + maxAmountLength + "s  " , expense.getAmount()));
            System.out.println(sb);
            sb = new StringBuilder();
        }



    }


    private int maxAmountLength(List<ExpenseResponse> expenses){
        int max = "Amount".length();
        for(ExpenseResponse expense : expenses){
            String amountString = String.valueOf(expense.getAmount());
            max = Math.max(max , amountString.length());
        }
        return max;
    }

    private int maxIdLength(List<ExpenseResponse> expenseResponses){
        int max = "ID".length();
        for(ExpenseResponse expense : expenseResponses){
            String idString = String.valueOf(expense.getId());
            max = Math.max(max , idString.length());
        }
        return max;
    }

    private int maxDescriptionLength(List<ExpenseResponse> expenseResponses){
        int max = "Description".length();
        for(ExpenseResponse expense : expenseResponses){
            max = Math.max(max , expense.getDescription().length());
        }
        return max;
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
