package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Comment;
import com.backend.helpdesk.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByTask(Task task);
}
