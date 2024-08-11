package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.LogEntryDTO;
import ru.ylab.hw1.enums.ActionType;

import java.time.LocalDate;
import java.util.List;

public interface LoggerService {

    void logAction(String username, ActionType actionType, String details);

    List<LogEntryDTO> getLogsByUser(String username);

    List<LogEntryDTO> getLogsByDate(LocalDate date);

    List<LogEntryDTO> getLogsByAction(ActionType actionType);

    void exportLogsToFile(String filePath);
}
