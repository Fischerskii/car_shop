package ru.ylab.hw.service;

import ru.ylab.hw.dto.LogEntry;
import ru.ylab.hw.enums.ActionType;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link LoggerService} interface that provides
 * methods for logging user actions and retrieving log entries.
 */
public interface LoggerService {

    /**
     * Logs a user action by saving a log entry to the repository.
     *
     * @param username   the username of the user performing the action
     * @param actionType the type of action being performed
     * @param details    additional details about the action
     */
    void logAction(String username, ActionType actionType, String details);

    /**
     * Retrieves log entries filtered by the specified username.
     *
     * @param username the username to filter log entries by
     * @return a list of {@link LogEntry} objects representing the log entries for the specified username
     */
    List<LogEntry> getLogsByUser(String username);

    /**
     * Retrieves log entries filtered by the specified date.
     *
     * @param date the date to filter log entries by
     * @return a list of {@link LogEntry} objects representing the log entries for the specified date
     */
    List<LogEntry> getLogsByDate(LocalDate date);

    List<LogEntry> getLogsByAction(ActionType actionType);

    void exportLogsToFile(String filePath);
}
