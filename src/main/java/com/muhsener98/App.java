package com.muhsener98;


import com.muhsener98.expense.tracker.cli.ApplicationCLI;
import com.muhsener98.expense.tracker.cli.OptionContainer;
import com.muhsener98.expense.tracker.cli.requestHandlers.*;
import com.muhsener98.expense.tracker.controller.ExpenseController;
import com.muhsener98.expense.tracker.controller.ExpenseControllerImpl;
import com.muhsener98.expense.tracker.repository.ExpenseBinaryFileRepository;
import com.muhsener98.expense.tracker.repository.ExpenseRepository;
import com.muhsener98.expense.tracker.service.ExpenseIdTracker;
import com.muhsener98.expense.tracker.service.ExpenseService;
import com.muhsener98.expense.tracker.service.ExpenseServiceImpl;
import com.muhsener98.expense.tracker.shared.mapper.ExpenseMapper;
import org.apache.commons.cli.Option;

import java.io.InputStream;
import java.net.URL;


public class App{
    public static void main(String[] args) {




        //TODO : write your own simple dependency injection framework
        ExpenseIdTracker expenseIdTracker = new ExpenseIdTracker();
        ExpenseRepository expenseRepository = new ExpenseBinaryFileRepository();
        ExpenseMapper expenseMapper = new ExpenseMapper();
        ExpenseService expenseService  = new ExpenseServiceImpl(expenseRepository , expenseMapper , expenseIdTracker);
        ExpenseController expenseController = new ExpenseControllerImpl(expenseService, expenseMapper);
        AddRequestHandler addRequestHandler = new AddRequestHandler(expenseController);
        OptionContainer optionContainer = new OptionContainer();
        ListRequestHandler listRequestHandler = new ListRequestHandler(expenseController    );
        SummaryRequestHandler summaryRequestHandler = new SummaryRequestHandler(expenseController);
        DeleteRequestHandler deleteRequestHandler = new DeleteRequestHandler(expenseController);
        UpdateRequestHandler updateRequestHandler = new UpdateRequestHandler(expenseController);

        ApplicationCLI applicationCLI = new ApplicationCLI(optionContainer , addRequestHandler, listRequestHandler,
                summaryRequestHandler, deleteRequestHandler , updateRequestHandler);


        applicationCLI.dispatchCommand(args);
    }
}