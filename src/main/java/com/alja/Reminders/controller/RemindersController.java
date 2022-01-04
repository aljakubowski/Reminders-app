package com.alja.Reminders.controller;

import com.alja.Reminders.reminders.Reminders;
import com.alja.Reminders.service.RemindersService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Reminders> getReminders(){
        return this.remindersService.getReminders();
    }

//    @GetMapping(path = "{id}")
//    public Reminders getReminder(){
//
//    }


    @PostMapping
    public void addReminder(@RequestBody Reminders reminder){
        remindersService.addNewReminder(reminder);
    }
}
