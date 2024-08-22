package ru.ylab.hw.repository;

import ru.ylab.hw.entity.LogEntry;
import ru.ylab.hw.enums.ActionType;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link LoggerRepository} interface for managing log entries in the database.
 */
public interface LoggerRepository {

    /**
     * Saves a log entry to the database.
     *
     * @param logEntry the log entry to be saved
     */
    void save(LogEntry logEntry);

    /**
     * Retrieves a list of log entries by username.
     *
     * @param username the username to filter logs by
     * @return a list of log entries associated with the specified username
     */
    List<LogEntry> findByUsername(String username);

    /**
     * Retrieves a list of log entries by action type.
     *
     * @param actionType the action type to filter logs by
     * @return a list of log entries with the specified action type
     */
    List<LogEntry> findByActionType(ActionType actionType);

    /**
     * Retrieves a list of log entries by date.
     *
     * @param date the date to filter logs by
     * @return a list of log entries from the specified date
     */
    List<LogEntry> findByDate(LocalDate date);

    /**
     * Retrieves all log entries from the database.
     *
     * @return a list of all log entries
     */
    List<LogEntry> findAll();

    /**
     * Finds log entries based on a given SQL query and criteria.
     *
     * @param query      the SQL query to be executed
     * @param criteria the criteria to filter logs by
     * @return a list of log entries matching the criteria
     */
    List<LogEntry> findLogsByCriteria(String query, String criteria);

}
