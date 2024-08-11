package ru.ylab.hw1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ylab.hw1.enums.ActionType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LogEntryDTO {
    private String username;
    private ActionType actionType;
    private String details;
    private LocalDateTime timestamp;

    public LogEntryDTO(String username, ActionType actionType, String details) {
        this.username = username;
        this.actionType = actionType;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
