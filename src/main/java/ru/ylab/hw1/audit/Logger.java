package ru.ylab.hw1.audit;

import ru.ylab.hw1.enums.ActionType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {
    private final List<LogEntry> logs = new ArrayList<>();

    public void log(String user, ActionType actionType, String message) {
        logs.add(new LogEntry(user, actionType, message));
    }

    public void viewLogs(String userFilter, ActionType actionTypeFilter, Date startDate, Date endDate) {
        logs.stream()
                .filter(log -> (userFilter == null || log.getUser().equals(userFilter)) &&
                        (actionTypeFilter == null || log.getActionType() == actionTypeFilter) &&
                        (startDate == null || !log.getTimestamp().before(startDate)) &&
                        (endDate == null || !log.getTimestamp().after(endDate)))
                .forEach(System.out::println);
    }

    public void exportLogs(String filename, String userFilter, ActionType actionTypeFilter, Date startDate, Date endDate) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (LogEntry log : logs) {
                if ((userFilter == null || log.getUser().equals(userFilter)) &&
                        (actionTypeFilter == null || log.getActionType() == actionTypeFilter) &&
                        (startDate == null || !log.getTimestamp().before(startDate)) &&
                        (endDate == null || !log.getTimestamp().after(endDate))) {
                    writer.write(log + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error exporting logs: " + e.getMessage());
        }
    }
}
