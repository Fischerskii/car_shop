package ru.ylab.hw1.audit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {
    private final List<String> logs = new ArrayList<>();

    public void log(String message) {
        logs.add(new Date() + ": " + message);
    }

    public void viewLogs() {
        logs.forEach(System.out::println);
    }

    public void exportLogs(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String log : logs) {
                writer.write(log + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error exporting logs: " + e.getMessage());
        }
    }
}
