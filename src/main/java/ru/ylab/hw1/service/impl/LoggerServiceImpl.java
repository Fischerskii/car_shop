package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.dto.LogEntryDTO;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.repository.LoggerRepository;
import ru.ylab.hw1.service.LoggerService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LoggerServiceImpl implements LoggerService {
    private final LoggerRepository loggerRepository;

    public LoggerServiceImpl(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void logAction(String username, ActionType actionType, String details) {
        LogEntryDTO logEntryDTO = new LogEntryDTO(username, actionType, details);
        loggerRepository.save(logEntryDTO);
    }

    @Override
    public List<LogEntryDTO> getLogsByUser(String username) {
        return loggerRepository.findByUsername(username);
    }

    @Override
    public List<LogEntryDTO> getLogsByDate(LocalDate date) {
        return loggerRepository.findByDate(date);
    }

    @Override
    public List<LogEntryDTO> getLogsByAction(ActionType actionType) {
        return loggerRepository.findByActionType(actionType);
    }

    @Override
    public void exportLogsToFile(String filePath) {
        List<LogEntryDTO> logs = loggerRepository.findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (LogEntryDTO log : logs) {
                writer.write(log.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}