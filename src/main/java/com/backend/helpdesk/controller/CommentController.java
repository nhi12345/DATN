package com.backend.helpdesk.controller;


import com.backend.helpdesk.DTO.CommentDTO;
import com.backend.helpdesk.DTO.JobDTO;
import com.backend.helpdesk.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/task/{id}")
    public List<CommentDTO> getCommentByTask(@PathVariable("id")int id) {
        return commentService.getCommentByTask(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @PostMapping("/task/{id}")
    public CommentDTO addComment(@PathVariable("id") int id, @RequestParam(value = "content", required = false) String content){
        return commentService.addComment(id,content);
    }

    @Secured("ROLE_EMPLOYEES")
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") int id){
        commentService.deleteComment(id);
    }
}