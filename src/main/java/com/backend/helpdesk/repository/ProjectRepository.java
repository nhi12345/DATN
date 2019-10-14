package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
}
