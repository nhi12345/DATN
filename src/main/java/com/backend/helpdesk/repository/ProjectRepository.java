package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    List<Project> findByStatus(Status status);
}
