package com.alja.Reminders.service;

import com.alja.Reminders.exceptionHandlers.*;
import com.alja.Reminders.reminders.Reminders;
import com.alja.Reminders.repository.RemindersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RemindersService {

    private final RemindersRepository remindersRepository;

    @Autowired
    public RemindersService(RemindersRepository repository) {
        this.remindersRepository = repository;
    }

    public void addNewReminder(Reminders reminders) {

        Optional<Reminders> remindersOptional = remindersRepository.findReminderByTask(reminders.getTask());
        if (remindersOptional.isPresent()) {
            throw new ReminderAlreadyExistsException
                    ("Reminder named: '" + reminders.getTask() + "' already exists. Same names are not allowed.");
        }

        if (reminders.getDaysLeft() < 0) {
            throw new ReminderDeadlineInPast
                    ("Deadline: " + reminders.getDeadline() + " is in the past. Cannot set new deadline");
        }

        this.remindersRepository.save(reminders);
    }

    public List<Reminders> getReminders() {
        return remindersRepository.findAll();
    }

    public Reminders getReminder(Long id) {
        return remindersRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder with id: '" + id + "' was not found."));
    }

    public void deleteReminder(Long id) {

        Reminders reminderToDelete = remindersRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException
                        ("Reminder with id: '" + id + "' was not found. Any reminder was deleted."));
        remindersRepository.deleteById(reminderToDelete.getId());
    }

    @Transactional
    public void makeDoneOrUndone(Long id, boolean isDone, String date) {

        Reminders reminderToUpdate = remindersRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException
                        ("Reminder with id: '" + id + "' was not found. Any reminder has changed its status."));
        if (isDone) {
            if (reminderToUpdate.isDone()) {
                throw new ReminderStatusException("The reminder has already status: 'done'");
            }
            reminderToUpdate.setDone(true);
            reminderToUpdate.setDeadline(null);
        } else {
            if (!reminderToUpdate.isDone()) {
                throw new ReminderStatusException("The reminder has already status: 'undone'");
            }
            reminderToUpdate.setDone(false);
            reminderToUpdate.setDeadline(LocalDate.parse(date));
        }
    }

    @Transactional
    public void updateReminder(Long id, Reminders reminderUpdateData) {

        Reminders reminderToUpdate = remindersRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException
                        ("Reminder with id: '" + id + "' was not found. Any reminder has changed its status."));

        if (reminderUpdateData.getDaysLeft() < 0) {
            throw new ReminderDeadlineInPast
                    ("New deadline: " + reminderUpdateData.getDeadline() + " is in the past. Cannot set deadline");
        }

        if (reminderToUpdate.getTask().equals(reminderUpdateData.getTask())
                && reminderToUpdate.getDetails().equals(reminderUpdateData.getDetails())
                && reminderToUpdate.getDeadline().equals(reminderUpdateData.getDeadline())) {
            throw new ReminderHasNotChanged("Any reminder data has changed.");
        }

        reminderToUpdate.setTask(reminderUpdateData.getTask());
        reminderToUpdate.setDetails(reminderUpdateData.getDetails());
        reminderToUpdate.setDeadline(reminderUpdateData.getDeadline());
    }
}