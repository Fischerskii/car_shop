package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.dto.LogEntry;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.repository.LoggerRepository;
import ru.ylab.hw1.service.LoggerService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link LoggerService} interface that provides
 * methods for logging user actions and retrieving log entries.
 */
public class LoggerServiceImpl implements LoggerService {
    private final LoggerRepository loggerRepository;

    /**
     * @param loggerRepository the logger repository to interact with the data source
     */
    public LoggerServiceImpl(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    /**
     * Logs a user action by saving a log entry to the repository.
     *
     * @param username   the username of the user performing the action
     * @param actionType the type of action being performed
     * @param details    additional details about the action
     */
    @Override
    public void logAction(String username, ActionType actionType, String details) {
        LogEntry logEntry = new LogEntry(username, actionType, details);
        loggerRepository.save(logEntry);
    }

    /**
     * Retrieves log entries filtered by the specified username.
     *
     * @param username the username to filter log entries by
     * @return a list of {@link LogEntry} objects representing the log entries for the specified username
     */
    @Override
    public List<LogEntry> getLogsByUser(String username) {
        return loggerRepository.findByUsername(username);
    }

    /**
     * Retrieves log entries filtered by the specified date.
     *
     * @param date the date to filter log entries by
     * @return a list of {@link LogEntry} objects representing the log entries for the specified date
     */
    @Override
    public List<LogEntry> getLogsByDate(LocalDate date) {
        return loggerRepository.findByDate(date);
    }

    /**
     * Retrieves log entries filtered by the specified action type.
     *
     * @param actionType the action type to filter log entries by
     * @return a list of {@link LogEntry} objects representing the log entries for the specified action type
     */
    @Override
    public List<LogEntry> getLogsByAction(ActionType actionType) {
        return loggerRepository.findByActionType(actionType);
    }

    /**
     * Exports all logs to a specified file.
     *
     * @param filePath the path to the file where logs will be exported
     */
    @Override
    public void exportLogsToFile(String filePath) {
        List<LogEntry> logs = loggerRepository.findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (LogEntry log : logs) {
                writer.write(log.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}