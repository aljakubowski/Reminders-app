package com.alja.Reminders.exceptionHandlers;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReminderErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public ReminderErrorResponse() {
    }

    public void setResponse(int status, String message, long timeStamp) {
        setStatus(status);
        setMessage(message);
        setTimeStamp(timeStamp);
    }
}