package ru.ylab.hw1.repository.impl;

import ru.ylab.hw1.dto.LogEntryDTO;
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

    public void save(LogEntryDTO logEntryDTO) {
        String sql = "INSERT INTO logs (username, action_type, details, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, logEntryDTO.getUsername());
            pstmt.setString(2, logEntryDTO.getActionType().name());
            pstmt.setString(3, logEntryDTO.getDetails());
            pstmt.setTimestamp(4, Timestamp.valueOf(logEntryDTO.getTimestamp()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LogEntryDTO> findByUsername(String username) {
        String sql = "SELECT * FROM logs WHERE username = ?";
        return findLogsByCriteria(sql, username);
    }

    public List<LogEntryDTO> findByDate(LocalDate date) {
        String sql = "SELECT * FROM logs WHERE DATE(timestamp) = ?";
        return findLogsByCriteria(sql, date.toString());
    }

    public List<LogEntryDTO> findByActionType(ActionType actionType) {
        String sql = "SELECT * FROM logs WHERE action_type = ?";
        return findLogsByCriteria(sql, actionType.name());
    }

    public List<LogEntryDTO> findAll() {
        String sql = "SELECT * FROM logs";
        List<LogEntryDTO> logs = new ArrayList<>();
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

    public List<LogEntryDTO> findLogsByCriteria(String sql, String criteria) {
        List<LogEntryDTO> logs = new ArrayList<>();
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

    private LogEntryDTO mapResultSetToLogEntry(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        ActionType actionType = ActionType.valueOf(rs.getString("action_type"));
        String details = rs.getString("details");
        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
        return new LogEntryDTO(username, actionType, details, timestamp);
    }
}