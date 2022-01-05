package com.alja.Reminders.exceptionHandlers;

public class ReminderAlreadyExistsException extends RuntimeException{
    public ReminderAlreadyExistsException(String message) {
        super(message);
    }
}
