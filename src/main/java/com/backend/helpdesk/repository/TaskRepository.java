package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Card;
import com.backend.helpdesk.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByCard(Card card);

    Optional<Task> findByName(String name);
}
