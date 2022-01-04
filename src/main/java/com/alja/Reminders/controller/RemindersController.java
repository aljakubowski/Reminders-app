package com.alja.Reminders.controller;

import com.alja.Reminders.reminders.Reminders;
import com.alja.Reminders.service.RemindersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "{id}")
    public Optional getReminder(@PathVariable("id") Long id){
        return this.remindersService.getReminder(id);
    }


    @PostMapping
    public void addReminder(@RequestBody Reminders reminder){
        remindersService.addNewReminder(reminder);
    }

    @DeleteMapping(path = "{id}")
    public void deleteReminder(@PathVariable("id") Long id){
        remindersService.deleteReminder(id);
    }

    @PutMapping(path = "{id}")
    public void updateReminder(@PathVariable("id") Long id, @RequestBody Reminders reminder){
        remindersService.updateReminder(id, reminder);
    }

}
