package com.muhsener98.expense.tracker.cli.requestHandlers;

import com.muhsener98.expense.tracker.cli.OptionContainer;
import com.muhsener98.expense.tracker.controller.ExpenseController;
import org.apache.commons.cli.CommandLine;

public class SummaryRequestHandler {

    private final ExpenseController expenseController;

    public SummaryRequestHandler(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    public void handle(CommandLine commandLine , OptionContainer optionContainer){
        String monthString = commandLine.getOptionValue("month");

        double summary = 0 ;
        int month = 0 ;
        if(monthString == null){
             summary = expenseController.getTotalSummary();
        }else {
             month = checkAndConvertMonthInput(monthString);
            summary = expenseController.getTotalSummaryByMonth(month);
        }
        System.out.println("# Total expenses for " + getMonthName(month)+ ": $" + summary);

    }


    private String getMonthName(int id){
        switch (id){
            case 1 : return  "January";
            case 2 : return  "February";
            case 3 : return  "March";
            case 4 : return  "April";
            case 5 : return  "May";
            case 6 : return  "June";
            case 7 : return  "July";
            case 8 : return  "August";
            case 9 : return  "September";
            case 10 : return  "October";
            case 11 : return  "November";
            case 12 : return  "December";
            default: return "";


        }
    }

    private int checkAndConvertMonthInput(String monthString){
        int month = 0 ;
        try {
            month =   Integer.parseInt(monthString);

            if(month < 1 || month > 12){
                System.err.println("wrong month input, enter month in range[1,12]");
                System.exit(-1);
            }


        }catch (NumberFormatException e){
            System.err.println("wrong number format for month!");
            System.exit(-1);
        }

        return month;


    }
}
