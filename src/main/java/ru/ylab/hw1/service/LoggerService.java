package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.LogEntry;
import ru.ylab.hw1.enums.ActionType;

import java.time.LocalDate;
import java.util.List;

public interface LoggerService {

    void logAction(String username, ActionType actionType, String details);

    List<LogEntry> getLogsByUser(String username);

    List<LogEntry> getLogsByDate(LocalDate date);

    List<LogEntry> getLogsByAction(ActionType actionType);

    void exportLogsToFile(String filePath);
}
