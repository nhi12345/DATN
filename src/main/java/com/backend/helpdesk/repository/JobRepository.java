package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Job;
import com.backend.helpdesk.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobRepository extends JpaRepository<Job,Integer> {
    List<Job> findByTask(Task task);
}
