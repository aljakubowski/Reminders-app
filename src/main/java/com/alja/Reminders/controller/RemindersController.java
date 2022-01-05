package com.alja.Reminders.controller;

import com.alja.Reminders.exceptionHandlers.*;
import com.alja.Reminders.reminders.Reminders;
import com.alja.Reminders.service.RemindersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "api/v1/reminders")
public class RemindersController {

    private final RemindersService remindersService;
    private final ReminderErrorResponse reminderErrorResponse;

    @Autowired
    public RemindersController(RemindersService remindersService, ReminderErrorResponse reminderErrorResponse) {
        this.remindersService = remindersService;
        this.reminderErrorResponse = reminderErrorResponse;
    }

    // == endpoint that shows list of all reminders in repository ==
    @GetMapping
    public CollectionModel<Reminders> getReminders() {

        List<Reminders> remindersList = remindersService.getReminders();

        Link remindersLink = linkTo(RemindersController.class).withSelfRel();

        remindersList.forEach(reminders -> reminders
                .add(linkTo(RemindersController.class).slash(reminders.getId()).withSelfRel()));

        return CollectionModel.of(remindersList, remindersLink);
    }

    // == endpoint that shows reminder by id number ==
    @GetMapping(path = "{id}")
    public ResponseEntity<Reminders> getReminder(@PathVariable("id") Long id) {

        Reminders reminderById = this.remindersService.getReminder(id);
        Link remindersLink = linkTo(RemindersController.class).withSelfRel();

        Link link = linkTo(RemindersController.class).slash(id).withSelfRel();
        reminderById.add(link).add(remindersLink);

        return new ResponseEntity<>(reminderById, HttpStatus.OK);
    }

    // == endpoint that allows to add reminder using body of the request (json) ==
    @PostMapping
    public ResponseEntity<Void> addReminder(@RequestBody Reminders reminders) {
        remindersService.addNewReminder(reminders);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // == endpoint that deletes reminder by id number ==
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable("id") Long id) {
        remindersService.deleteReminder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // == endpoint that makes reminder field 'done' true or false
    //    if reminder is being set to false - new deadline is required ==
    @PutMapping(path = "/done/{id}")
    public ResponseEntity<Void> makeDoneOrUndone(@PathVariable("id") Long id,
                                                 @RequestParam("isdone") boolean isdone,
                                                 @RequestParam(value = "newdeadline", required = false) String newDeadline) {
        remindersService.makeDoneOrUndone(id, isdone, newDeadline);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // == endpoint that updates reminder ==
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Void> updateReminder(@PathVariable("id") Long id,
                                               @RequestBody Reminders reminderUpdateData) {
        remindersService.updateReminder(id, reminderUpdateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderNotFoundException e) {
        reminderErrorResponse.setResponse
                (HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(reminderErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderAlreadyExistsException e) {
        reminderErrorResponse.setResponse
                (HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(reminderErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderStatusException e) {
        reminderErrorResponse.setResponse
                (HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(reminderErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderDeadlineInPast e) {
        reminderErrorResponse.setResponse
                (HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(reminderErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ReminderErrorResponse> handleException(ReminderHasNotChanged e) {
        reminderErrorResponse.setResponse
                (HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(reminderErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}