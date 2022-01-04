package com.alja.Reminders.service;

import com.alja.Reminders.reminders.Reminders;
import com.alja.Reminders.repository.RemindersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemindersService {

    private final RemindersRepository remindersRepository;

    @Autowired
    public RemindersService(RemindersRepository repository) {
        this.remindersRepository = repository;
    }

    public List<Reminders> getReminders(){
        return remindersRepository.findAll();
    }

    public void addNewReminder(Reminders reminder) {
        Optional<Reminders> remindersOptional = remindersRepository.findReminderByTask(reminder.getTask());
        if (remindersOptional.isPresent()){
            throw new IllegalStateException("task already exists");
        }
        this.remindersRepository.save(reminder);
    }
}
