package com.muhsener98.expense.tracker.cli;

import com.muhsener98.expense.tracker.cli.exception.NoSuchCommandException;
import com.muhsener98.expense.tracker.cli.requestHandlers.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class ApplicationCLI {


    private final OptionContainer optionContainer;

    private final AddRequestHandler addRequestHandler;
    private final ListRequestHandler listRequestHandler;
    private final SummaryRequestHandler summaryRequestHandler;
    private final DeleteRequestHandler deleteRequestHandler ;
    private final UpdateRequestHandler updateRequestHandler;


    public ApplicationCLI(OptionContainer optionContainer, AddRequestHandler addRequestHandler,
                          ListRequestHandler listRequestHandler, SummaryRequestHandler summaryRequestHandler,
                          DeleteRequestHandler deleteRequestHandler, UpdateRequestHandler updateRequestHandler) {
        this.optionContainer = optionContainer;
        this.addRequestHandler = addRequestHandler;
        this.listRequestHandler = listRequestHandler;
        this.summaryRequestHandler = summaryRequestHandler;
        this.deleteRequestHandler = deleteRequestHandler;
        this.updateRequestHandler = updateRequestHandler;
    }


    public void dispatchCommand(String[] args) {
        if (args.length == 0) {
            optionContainer.printHelp(null);
            System.exit(-1);
        }

        String command = args[0];
        CommandLine commandLine = null;
        try {
            commandLine = optionContainer.parse(command, args);
        } catch (NoSuchCommandException e) {
            optionContainer.printHelp(null);
            System.exit(-1);
        } catch (ParseException e) {
            optionContainer.printHelp(command);
            System.exit(-1);
        }


        if (command.equalsIgnoreCase(CommandNames.ADD.getVal())) {
            addRequestHandler.handle(commandLine , optionContainer);
        }else if(command.equals(CommandNames.LIST.getVal())){
            listRequestHandler.handle(commandLine , optionContainer);
        }else if(command.equalsIgnoreCase(CommandNames.SUMMARY.getVal())){
            summaryRequestHandler.handle(commandLine , optionContainer);
        }else if(command.equalsIgnoreCase(CommandNames.DELETE.getVal())){
            deleteRequestHandler.handle(commandLine , optionContainer);
        }else if(command.equalsIgnoreCase(CommandNames.UPDATE.getVal()))
            updateRequestHandler.handle(commandLine , optionContainer);;


    }

}
