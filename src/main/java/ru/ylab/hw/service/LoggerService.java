package ru.ylab.hw.service;

import ru.ylab.hw.entity.LogEntry;
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

    /**
     * Retrieves log entries filtered by the specified action type.
     *
     * @param actionType the action type to filter log entries by
     * @return a list of {@link LogEntry} objects representing the log entries for the specified action type
     */
    List<LogEntry> getLogsByAction(ActionType actionType);

    /**
     * Exports all logs to a specified file.
     *
     * @param filePath the path to the file where logs will be exported
     */
    void exportLogsToFile(String filePath);
}
