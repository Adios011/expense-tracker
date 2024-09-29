package com.muhsener98.expense.tracker.repository;

import com.muhsener98.expense.tracker.entity.Expense;
import com.muhsener98.expense.tracker.exception.DatabaseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ExpenseBinaryFileRepository implements ExpenseRepository {

    private static final int DESCRIPTION_MAX_LENGTH = 50;
    private static final int RECORD_LENGTH = 70;
    private static final String FIELD_NAME = "expenses.txt";
    private static final RandomAccessFile FILE;
    private static final String FIELD_PATH;

    static {
        //TODO : hard-coded file path
        FIELD_PATH =  "/Users/muhammetsener/Desktop/roadmap-projects/expense-tracker/expenses.txt";
        try {
            FILE = new RandomAccessFile(FIELD_PATH, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Expense save(Expense expense) {
        if (expense.getId() == null || expense.getCreatedAt() == null)
            throw new DatabaseException("Not initialized expense to persist");


        try {
            FILE.seek(FILE.length()); // Go to the end of the file.

            int id = expense.getId();
            FILE.writeInt(id);

            String description = String.format("%-" + DESCRIPTION_MAX_LENGTH + "s", expense.getDescription());
            FILE.writeBytes(description);

            double amount = expense.getAmount();
            FILE.writeDouble(amount);

            long date = expense.getCreatedAt().getTime();
            FILE.writeLong(date);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return expense;
    }

    @Override
    public Expense findById(int id) {
        try {
            FILE.seek((long) RECORD_LENGTH * (id - 1));

            int id2 = FILE.readInt();

            byte[] descriptionBytes = new byte[DESCRIPTION_MAX_LENGTH];
            FILE.read(descriptionBytes);
            String description = new String(descriptionBytes).trim();

            double amount = FILE.readDouble();

            Date date = new Date(FILE.readLong());

            return Expense.Builder.builder()
                    .id(id2)
                    .amount(amount)
                    .description(description)
                    .createdAt(date)
                    .build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Expense> findAll() {
        List<Expense> result = new ArrayList<>();
        int readCounter = 0;
        long filePointer = 0;
        long fileLength = 0;
        try {
            fileLength = FILE.length();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while (filePointer + RECORD_LENGTH <= fileLength) {
            try {
                FILE.seek(filePointer);
                filePointer += RECORD_LENGTH;

                int id = FILE.readInt();
                if (id < 0) continue; // It means that the record has been deleted. (soft delete)

                byte[] descriptionBytes = new byte[DESCRIPTION_MAX_LENGTH];
                FILE.read(descriptionBytes);
                String description = new String(descriptionBytes).trim();


                double amount = FILE.readDouble();

                Date date = new Date(FILE.readLong());

                Expense expense = Expense.Builder.builder()
                        .id(id)
                        .amount(amount)
                        .description(description)
                        .createdAt(date)
                        .build();

                result.add(expense);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        return result;
    }

    @Override
    public Expense deleteById(int id) {
        try {
            FILE.seek((long) (id - 1) * RECORD_LENGTH  );

            int idInFile = FILE.readInt();

            if(id != idInFile)
                return null;

            FILE.seek((long) (id - 1) * RECORD_LENGTH  );
            FILE.writeInt(-1);

            String description = readDescriptionFromFile();
            double amount = FILE.readDouble();
            Date createdAt = new Date(FILE.readLong());

            return Expense.Builder.builder()
                    .id(id)
                    .description(description)
                    .amount(amount)
                    .createdAt(createdAt)
                    .build();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Expense update(Expense expenseToBeUpdated) {
        int id = expenseToBeUpdated.getId();
        try {
            FILE.seek((long) RECORD_LENGTH * (id-1));

            int idInFile = FILE.readInt();
            if(idInFile != id)
                return null;

            FILE.writeBytes(String.format("%-"+DESCRIPTION_MAX_LENGTH+"s", expenseToBeUpdated.getDescription() ));
            FILE.writeDouble(expenseToBeUpdated.getAmount());



            return expenseToBeUpdated;


        } catch (IOException e) {
            return null;
        }
    }


    private String readDescriptionFromFile() throws IOException {
        byte[] descriptionBytes = new byte[DESCRIPTION_MAX_LENGTH];
        FILE.read(descriptionBytes);
        return new String(descriptionBytes).trim();
    }


}
