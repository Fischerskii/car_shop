package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.LogEntryDTO;
import ru.ylab.hw1.enums.ActionType;

import java.time.LocalDate;
import java.util.List;

public interface LoggerRepository {

    void save(LogEntryDTO logEntryDTO);

    List<LogEntryDTO> findByUsername(String username);

    List<LogEntryDTO> findByActionType(ActionType actionType);

    List<LogEntryDTO> findByDate(LocalDate date);

    List<LogEntryDTO> findAll();

    List<LogEntryDTO> findLogsByCriteria(String sql, String criteria);

}
