package com.muhsener98.expense.tracker.cli;

import com.muhsener98.expense.tracker.cli.exception.NoSuchCommandException;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class OptionContainer {

    private final Map<String, Options> optionsMap ;
    private final CommandLineParser commandLineParser;
    private final HelpFormatter helpFormatter;


    public OptionContainer() {
        this.optionsMap = new HashMap<>();
        fillMap();
        this.commandLineParser = new DefaultParser();
        helpFormatter = new HelpFormatter();
    }

    public void printHelp(String command){
        if(command == null){
            for(Map.Entry<String,Options> entry : optionsMap.entrySet()){
                String commandName = entry.getKey();
                helpFormatter.printHelp( commandName + " [options]" , entry.getValue());
            }
        }else {
            helpFormatter.printHelp(command + " [options] " , optionsMap.get(command));

        }
    }

    public Options getOptions(String command) throws NoSuchCommandException {
        if(optionsMap.containsKey(command))
            return optionsMap.get(command);
        else
            throw new NoSuchCommandException("no such command: " + command);
    }

    public CommandLine parse(String command, String... args) throws NoSuchCommandException, ParseException {
        Options options = getOptions(command);
        return commandLineParser.parse(options,args);
    }


    private void fillMap() {
        Properties properties = readOptionsFromPropertiesFile();

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            if (key.startsWith("option")) {
                String value = (String) entry.getValue();
                String[] args = value.split(",");

                if(args.length == 1){
                    optionsMap.put(args[0], new Options());
                    continue;
                }

                Option option = new Option(args[1], args[2], Boolean.parseBoolean(args[3]), args[4]);

                if (optionsMap.containsKey(args[0])) {
                    optionsMap.get(args[0]).addOption(option);
                } else
                    optionsMap.put(args[0], new Options().addOption(option));

            }
        }
    }

    private Properties readOptionsFromPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
