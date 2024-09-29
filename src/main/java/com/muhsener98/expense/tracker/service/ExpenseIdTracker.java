package com.muhsener98.expense.tracker.service;

import java.io.*;
import java.util.Scanner;

public class ExpenseIdTracker {

    public File file;

    public ExpenseIdTracker() {

//        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("IDRepository.cfg");
        String filePath = "/Users/muhammetsener/Desktop/roadmap-projects/expense-tracker/IDRepository.cfg";
        System.out.println(filePath);

        file = new File(filePath);

    }

    public int getNextId() {
        int currentId = 0;
        try {
            currentId = readCurrentId();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        updateId(++currentId);
        return currentId;
    }


    private void updateId(int id) {


        try (FileWriter fileWriter = new FileWriter(file)) {
            String line = "currentId=" + id;
            fileWriter.write(line);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int readCurrentId() throws FileNotFoundException {

        try {
            Scanner scanner = new Scanner(new FileInputStream(file));

            int id = -1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("currentId")) {
                    id = Integer.parseInt(line.split("=")[1].trim());
                    break;
                }
            }

            if (id < 0)
                throw new RuntimeException("No currentId definition or it is invalid in file " + file.getName());

            scanner.close();
            return id;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFile(File file) {
        this.file = file;
    }
}
