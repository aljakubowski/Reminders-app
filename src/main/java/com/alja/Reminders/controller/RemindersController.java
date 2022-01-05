package com.alja.Reminders.controller;

import com.alja.Reminders.exceptionHandlers.*;
import com.alja.Reminders.reminders.Reminders;
import com.alja.Reminders.service.RemindersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/reminders")
public class RemindersController {

    private final RemindersService remindersService;

    @Autowired
    public RemindersController(RemindersService remindersService) {
        this.remindersService = remindersService;
    }

    // == endpoint that shows list of all reminders in repository ==
    @GetMapping
    public List<Reminders> getReminders() {
        return remindersService.getReminders();
    }

    // == endpoint that shows reminder by id number ==
    @GetMapping(path = "{id}")
    public Reminders getReminder(@PathVariable("id") Long id) {
        return this.remindersService.getReminder(id);
    }

    // == endpoint that allows to add reminder using body of the request (json) ==
    @PostMapping
    public void addReminder(@RequestBody Reminders reminders) {
        remindersService.addNewReminder(reminders);
    }

    // == endpoint that deletes reminder by id number ==
    @DeleteMapping(path = "{id}")
    public void deleteReminder(@PathVariable("id") Long id) {
        remindersService.deleteReminder(id);
    }

    // == endpoint that makes reminder field 'done' true or false
    //    if reminder is being set to false - new deadline is required ==
    @PutMapping(path = "/done/{id}")
    public void makeDoneOrUndone(@PathVariable("id") Long id,
                                 @RequestParam("isdone") boolean isdone,
                                 @RequestParam(value = "newdeadline", required = false) String newDeadline) {
        remindersService.makeDoneOrUndone(id, isdone, newDeadline);
    }


    // == endpoint that updates reminder ==
    @PutMapping(path = "/update/{id}")
    public void updateReminder(@PathVariable("id") Long id,
                               @RequestBody Reminders reminderUpdateData) {
        remindersService.updateReminder(id, reminderUpdateData);
    }


    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderNotFoundException e) {
        ReminderErrorResponse error = new ReminderErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderAlreadyExistsException e) {
        ReminderErrorResponse error = new ReminderErrorResponse();
        error.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderStatusException e) {
        ReminderErrorResponse error = new ReminderErrorResponse();
        error.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderDeadlineInPast e) {
        ReminderErrorResponse error = new ReminderErrorResponse();
        error.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderHasNotChanged e) {
        ReminderErrorResponse error = new ReminderErrorResponse();
        error.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }
}