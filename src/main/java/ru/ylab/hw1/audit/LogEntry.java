package ru.ylab.hw1.audit;

import lombok.Data;
import ru.ylab.hw1.enums.ActionType;

import java.util.Date;

@Data
public class LogEntry {
    private final Date timestamp;
    private final String user;
    private final ActionType actionType;
    private final String message;

    public LogEntry(String user, ActionType actionType, String message) {
        this.timestamp = new Date();
        this.user = user;
        this.actionType = actionType;
        this.message = message;
    }
}