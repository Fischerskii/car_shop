package ru.ylab.hw.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ylab.hw.config.DatabaseConfig;
import ru.ylab.hw.entity.LogEntry;
import ru.ylab.hw.enums.ActionType;
import ru.ylab.hw.exception.DataAccessException;
import ru.ylab.hw.repository.LoggerRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class LoggerRepositoryImpl implements LoggerRepository {

    @Override
    public void save(LogEntry logEntry) {
        String query = "INSERT INTO logs (id, username, action_type, log_message, log_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = new DatabaseConfig().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setObject(1, logEntry.getId());
                statement.setString(2, logEntry.getUsername());
                statement.setString(3, logEntry.getActionType().toString());
                statement.setString(4, logEntry.getLogMessage());
                statement.setTimestamp(5, Timestamp.valueOf(logEntry.getTimestamp()));

                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error saving log entry with id: {}", logEntry.getId(), e);
                throw new DataAccessException("Error saving log entry with id: " + logEntry.getId(), e);
            }
        } catch (SQLException e) {
            log.error("Failed to save log entry", e);
        }
    }

    @Override
    public List<LogEntry> findByUsername(String username) {
        String query = "SELECT * FROM logs WHERE username = ?";
        return findLogsByCriteria(query, username);
    }

    @Override
    public List<LogEntry> findByActionType(ActionType actionType) {
        String query = "SELECT * FROM logs WHERE action_type = ?";
        return findLogsByCriteria(query, actionType.toString());
    }

    @Override
    public List<LogEntry> findByDate(LocalDate date) {
        String query = "SELECT * FROM logs WHERE log_date = ?";
        return findLogsByCriteria(query, Date.valueOf(date).toString());
    }

    @Override
    public List<LogEntry> findAll() {
        String query = "SELECT * FROM logs";
        List<LogEntry> logs = new ArrayList<>();

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LogEntry logEntry = mapRowToLogEntry(resultSet);
                logs.add(logEntry);
            }
        } catch (SQLException e) {
            log.error("Error finding logs", e);
        }

        return logs;
    }

    @Override
    public List<LogEntry> findLogsByCriteria(String query, String criteria) {
        List<LogEntry> logs = new ArrayList<>();

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, criteria);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LogEntry logEntry = mapRowToLogEntry(resultSet);
                    logs.add(logEntry);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding logs by criteria", e);
        }

        return logs;
    }

    private LogEntry mapRowToLogEntry(ResultSet resultSet) throws SQLException {
        UUID id = UUID.fromString(resultSet.getString("id"));
        String username = resultSet.getString("username");
        ActionType actionType = ActionType.valueOf(resultSet.getString("action_type"));
        String logMessage = resultSet.getString("log_message");
        LocalDateTime timestamp = resultSet.getTimestamp("log_date").toLocalDateTime();

        return LogEntry.builder()
                .id(id)
                .username(username)
                .actionType(actionType)
                .logMessage(logMessage)
                .timestamp(timestamp)
                .build();
    }
}
