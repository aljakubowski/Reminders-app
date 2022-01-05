package com.alja.Reminders.exceptionHandlers;

import lombok.Data;

@Data
public class ReminderErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public ReminderErrorResponse() {
    }
}
