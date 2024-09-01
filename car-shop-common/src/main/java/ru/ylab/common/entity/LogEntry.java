package ru.ylab.common.entity;

import lombok.Builder;
import lombok.Data;
import ru.ylab.common.enums.ActionType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LogEntry {
    private final UUID id;
    private final String username;
    private final ActionType actionType;
    private final String logMessage;
    private final LocalDateTime timestamp;

    public LogEntry(UUID id, String username, ActionType actionType, String logMessage, LocalDateTime timestamp) {
        this.id = id;
        this.username = username;
        this.actionType = actionType;
        this.logMessage = logMessage;
        this.timestamp = timestamp;
    }

    public LogEntry(String username, ActionType actionType, String logMessage) {
        this(UUID.randomUUID(), username, actionType, logMessage, LocalDateTime.now());
    }
}
