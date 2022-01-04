package com.alja.Reminders.repository;

import com.alja.Reminders.reminders.Reminders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RemindersRepository extends JpaRepository<Reminders, Long> {

    Optional<Reminders> findReminderByTask(String task);
}
