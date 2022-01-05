package com.alja.Reminders.exceptionHandlers;

public class ReminderHasNotChanged extends RuntimeException{
    public ReminderHasNotChanged(String message) {
        super(message);
    }
}
