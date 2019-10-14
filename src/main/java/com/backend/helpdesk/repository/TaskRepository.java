package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
