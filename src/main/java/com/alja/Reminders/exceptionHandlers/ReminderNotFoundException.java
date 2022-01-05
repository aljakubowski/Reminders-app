package com.alja.Reminders.exceptionHandlers;

public class ReminderNotFoundException extends RuntimeException{
    public ReminderNotFoundException(String message) {
        super(message);
    }
}
