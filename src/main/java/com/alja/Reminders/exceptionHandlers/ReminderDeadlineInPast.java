package com.alja.Reminders.exceptionHandlers;

public class ReminderDeadlineInPast extends RuntimeException{
    public ReminderDeadlineInPast(String message) {
        super(message);
    }
}
