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

//    public Reminders getReminder(Long id){
//        if (checkIfReminderExists(id)){
//            return remindersRepository.getById(id);
//        }
//        return null;
//    }

    public Optional getReminder(Long id){
        Optional<Reminders> remindersOptional = remindersRepository.findById(id);
        if (remindersOptional.isEmpty()){
            throw new IllegalStateException("task with id: '" + id + "' does exists");
        }
        return remindersOptional;
    }

//    public Optional getEmployee(Long id){
//
//        Optional<Employee> employeeOptional = employeeRepository.findById(id);
//        if (employeeOptional.isEmpty()){
//            throw new IllegalStateException("Employee with id: " + id + " is not present");
//        }
//        return employeeOptional;
//
//    }

    public void addNewReminder(Reminders reminder) {
        Optional<Reminders> remindersOptional = remindersRepository.findReminderByTask(reminder.getTask());
        if (remindersOptional.isPresent()){
            throw new IllegalStateException("task already exists");
        }
        this.remindersRepository.save(reminder);
    }

    public void deleteReminder(Long id) {
        if (checkIfReminderExists(id)){
            this.remindersRepository.deleteById(id);
        }
    }

    private boolean checkIfReminderExists(Long id){
        if (!this.remindersRepository.existsById(id)) {
            throw new IllegalStateException("Reminder with id: " + id + " does not exist");
        }
        return true;
    }

    public void updateReminder(Long id, Reminders reminder) {

    }
}
