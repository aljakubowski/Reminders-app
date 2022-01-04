package com.alja.Reminders.reminders;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table
public class Reminders {

    @Id
    @SequenceGenerator(name = "reminders_id_seq", sequenceName = "reminders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reminders_id_seq")
    private Long id;

    private String task;
    private String details;

    @CreationTimestamp
    private LocalDateTime created;
    private LocalDate deadline;

    @Transient
    private long daysLeft;
    private boolean done;

    public Reminders() {
    }

    public Reminders(String task, String details, LocalDate deadline) {
        this.task = task;
        this.details = details;
        this.created = LocalDateTime.now();
        this.deadline = deadline;
        this.done = false;
    }



    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

    public String getCreated() {
        return formatDate(this.created);
    }

    public long getDaysLeft() {
        return Duration.between(LocalDate.now().atStartOfDay(), deadline.atStartOfDay()).toDays();
    }
}