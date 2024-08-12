package ru.ylab.hw1.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.dto.LogEntry;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.repository.LoggerRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link LoggerRepository} interface for managing log entries in the database.
 */
@Slf4j
public class LoggerRepositoryImpl implements LoggerRepository {

    private final Connection connection;

    /**
     * @param connection the database connection to be used by this repository
     */
    public LoggerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves a log entry to the database.
     *
     * @param logEntry the log entry to be saved
     */
    @Override
    public void save(LogEntry logEntry) {
        String sql = "INSERT INTO logs (username, action_type, details, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, logEntry.getUsername());
            pstmt.setString(2, logEntry.getActionType().name());
            pstmt.setString(3, logEntry.getDetails());
            pstmt.setTimestamp(4, Timestamp.valueOf(logEntry.getTimestamp()));
            pstmt.executeUpdate();
            log.info("Log entry for user {} saved successfully", logEntry.getUsername());
        } catch (SQLException e) {
            log.error("Error saving log entry for user {}: {}", logEntry.getUsername(), e.getMessage(), e);
        }
    }

    /**
     * Retrieves a list of log entries by username.
     *
     * @param username the username to filter logs by
     * @return a list of log entries associated with the specified username
     */
    @Override
    public List<LogEntry> findByUsername(String username) {
        String sql = "SELECT * FROM logs WHERE username = ?";
        return findLogsByCriteria(sql, username);
    }

    /**
     * Retrieves a list of log entries by date.
     *
     * @param date the date to filter logs by
     * @return a list of log entries from the specified date
     */
    @Override
    public List<LogEntry> findByDate(LocalDate date) {
        String sql = "SELECT * FROM logs WHERE DATE(timestamp) = ?";
        return findLogsByCriteria(sql, date.toString());
    }

    /**
     * Retrieves a list of log entries by action type.
     *
     * @param actionType the action type to filter logs by
     * @return a list of log entries with the specified action type
     */
    @Override
    public List<LogEntry> findByActionType(ActionType actionType) {
        String sql = "SELECT * FROM logs WHERE action_type = ?";
        return findLogsByCriteria(sql, actionType.name());
    }

    /**
     * Retrieves all log entries from the database.
     *
     * @return a list of all log entries
     */
    @Override
    public List<LogEntry> findAll() {
        String sql = "SELECT * FROM logs";
        List<LogEntry> logs = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(mapResultSetToLogEntry(rs));
            }
        } catch (SQLException e) {
            log.error("Error retrieving all logs: {}", e.getMessage(), e);
        }
        return logs;
    }

    /**
     * Finds log entries based on a given SQL query and criteria.
     *
     * @param sql      the SQL query to be executed
     * @param criteria the criteria to filter logs by
     * @return a list of log entries matching the criteria
     */
    @Override
    public List<LogEntry> findLogsByCriteria(String sql, String criteria) {
        List<LogEntry> logs = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, criteria);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToLogEntry(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Error retrieving logs with criteria {}: {}", criteria, e.getMessage(), e);
        }
        return logs;
    }

    /**
     * Maps a {@link ResultSet} to a {@link LogEntry} object.
     *
     * @param rs the ResultSet to be mapped
     * @return the corresponding LogEntry object
     * @throws SQLException if an error occurs while accessing the ResultSet
     */
    private LogEntry mapResultSetToLogEntry(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        ActionType actionType = ActionType.valueOf(rs.getString("action_type"));
        String details = rs.getString("details");
        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
        return new LogEntry(username, actionType, details, timestamp);
    }
}