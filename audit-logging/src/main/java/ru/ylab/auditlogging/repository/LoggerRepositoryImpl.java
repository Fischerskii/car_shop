package ru.ylab.auditlogging.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.ylab.common.entity.LogEntry;
import ru.ylab.common.enums.ActionType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class LoggerRepositoryImpl implements LoggerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoggerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(LogEntry logEntry) {
        String query = "INSERT INTO logs (id, username, action_type, log_message, log_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(query,
                    logEntry.getId(),
                    logEntry.getUsername(),
                    logEntry.getActionType().toString(),
                    logEntry.getLogMessage(),
                    logEntry.getTimestamp()
            );
        } catch (Exception e) {
            log.error("Error saving log entry with id: {}", logEntry.getId(), e);
        }
    }

    @Override
    public List<LogEntry> findByUsername(String username) {
        String query = "SELECT * FROM logs WHERE username = ?";
        return jdbcTemplate.query(query, new Object[]{username}, logEntryRowMapper());
    }

    @Override
    public List<LogEntry> findByActionType(ActionType actionType) {
        String query = "SELECT * FROM logs WHERE action_type = ?";
        return jdbcTemplate.query(query, new Object[]{actionType.toString()}, logEntryRowMapper());
    }

    @Override
    public List<LogEntry> findByDate(LocalDate date) {
        String query = "SELECT * FROM logs WHERE log_date = ?";
        return jdbcTemplate.query(query, new Object[]{java.sql.Date.valueOf(date)}, logEntryRowMapper());
    }

    @Override
    public List<LogEntry> findAll() {
        String query = "SELECT * FROM logs";
        return jdbcTemplate.query(query, logEntryRowMapper());
    }

    @Override
    public List<LogEntry> findLogsByCriteria(String query, String criteria) {
        return jdbcTemplate.query(query, new Object[]{criteria}, logEntryRowMapper());
    }

    private RowMapper<LogEntry> logEntryRowMapper() {
        return new RowMapper<LogEntry>() {
            @Override
            public LogEntry mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
        };
    }
}
