package ru.ylab.hw1.repository.impl;

import ru.ylab.hw1.dto.LogEntry;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.repository.LoggerRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoggerRepositoryImpl implements LoggerRepository {

    private final Connection connection;

    public LoggerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(LogEntry logEntry) {
        String sql = "INSERT INTO logs (username, action_type, details, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, logEntry.getUsername());
            pstmt.setString(2, logEntry.getActionType().name());
            pstmt.setString(3, logEntry.getDetails());
            pstmt.setTimestamp(4, Timestamp.valueOf(logEntry.getTimestamp()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LogEntry> findByUsername(String username) {
        String sql = "SELECT * FROM logs WHERE username = ?";
        return findLogsByCriteria(sql, username);
    }

    public List<LogEntry> findByDate(LocalDate date) {
        String sql = "SELECT * FROM logs WHERE DATE(timestamp) = ?";
        return findLogsByCriteria(sql, date.toString());
    }

    public List<LogEntry> findByActionType(ActionType actionType) {
        String sql = "SELECT * FROM logs WHERE action_type = ?";
        return findLogsByCriteria(sql, actionType.name());
    }

    public List<LogEntry> findAll() {
        String sql = "SELECT * FROM logs";
        List<LogEntry> logs = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(mapResultSetToLogEntry(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

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
            e.printStackTrace();
        }
        return logs;
    }

    private LogEntry mapResultSetToLogEntry(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        ActionType actionType = ActionType.valueOf(rs.getString("action_type"));
        String details = rs.getString("details");
        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
        return new LogEntry(username, actionType, details, timestamp);
    }
}