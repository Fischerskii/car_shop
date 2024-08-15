package ru.ylab.hw.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw.dto.LogEntry;
import ru.ylab.hw.enums.ActionType;
import ru.ylab.hw.repository.LoggerRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoggerRepositoryImpl implements LoggerRepository {

    private final Connection connection;

    public LoggerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(LogEntry logEntry) {
        String sql = "INSERT INTO service_schema.logs (username, action_type, details, timestamp) VALUES (?, ?, ?, ?)";
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

    @Override
    public List<LogEntry> findByUsername(String username) {
        String query = "SELECT * FROM logs WHERE username = ?";
        return findLogsByCriteria(query, username);
    }

    @Override
    public List<LogEntry> findByDate(LocalDate date) {
        String query = "SELECT * FROM service_schema.logs WHERE DATE(timestamp) = ?";
        return findLogsByCriteria(query, date.toString());
    }

    @Override
    public List<LogEntry> findByActionType(ActionType actionType) {
        String query = "SELECT * FROM logs WHERE action_type = ?";
        return findLogsByCriteria(query, actionType.name());
    }

    @Override
    public List<LogEntry> findAll() {
        String sql = "SELECT * FROM service_schema.logs";
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

    @Override
    public List<LogEntry> findLogsByCriteria(String query, String criteria) {
        List<LogEntry> logs = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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