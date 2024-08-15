package ru.ylab.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ylab.hw.enums.ActionType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LogEntry {
    private String username;
    private ActionType actionType;
    private String details;
    private LocalDateTime timestamp;

    public LogEntry(String username, ActionType actionType, String details) {
        this.username = username;
        this.actionType = actionType;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
