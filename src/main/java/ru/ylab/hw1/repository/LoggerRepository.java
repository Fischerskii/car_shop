package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.LogEntry;
import ru.ylab.hw1.enums.ActionType;

import java.time.LocalDate;
import java.util.List;

public interface LoggerRepository {

    void save(LogEntry logEntry);

    List<LogEntry> findByUsername(String username);

    List<LogEntry> findByActionType(ActionType actionType);

    List<LogEntry> findByDate(LocalDate date);

    List<LogEntry> findAll();

    List<LogEntry> findLogsByCriteria(String sql, String criteria);

}
