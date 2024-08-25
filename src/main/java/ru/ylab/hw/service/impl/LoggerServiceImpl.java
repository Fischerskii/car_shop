package ru.ylab.hw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ylab.hw.entity.LogEntry;
import ru.ylab.hw.enums.ActionType;
import ru.ylab.hw.repository.LoggerRepository;
import ru.ylab.hw.service.LoggerService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoggerServiceImpl implements LoggerService {
    private final LoggerRepository loggerRepository;

    @Autowired
    public LoggerServiceImpl(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void logAction(String username, ActionType actionType, String details) {
        LogEntry logEntry = new LogEntry(username, actionType, details);
        loggerRepository.save(logEntry);
    }

    @Override
    public List<LogEntry> getLogsByUser(String username) {
        return loggerRepository.findByUsername(username);
    }

    @Override
    public List<LogEntry> getLogsByDate(LocalDate date) {
        return loggerRepository.findByDate(date);
    }

    @Override
    public List<LogEntry> getLogsByAction(ActionType actionType) {
        return loggerRepository.findByActionType(actionType);
    }

    @Override
    public void exportLogsToFile(String filePath) {
        List<LogEntry> logs = loggerRepository.findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (LogEntry log : logs) {
                writer.write(log.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}