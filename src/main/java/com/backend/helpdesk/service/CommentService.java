package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.CommentDTO;
import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Comment;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.CommentRepository;
import com.backend.helpdesk.repository.TaskRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Converter<Comment,CommentDTO> commentCommentDTOConverter;

    @Autowired
    private Converter<CommentDTO,Comment> commentDTOCommentConverter;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    private Converter<Task, TaskDTO> taskTaskDTOConverter;


    public List<CommentDTO> getCommentByTask(int id){
        Optional<Task> task=taskRepository.findById(id);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found");
        }
        return commentCommentDTOConverter.convert(commentRepository.findByTask(task.get()));
    }

    public CommentDTO addComment(int id,String content){
        Optional<Task> task=taskRepository.findById(id);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found");
        }
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setUserEntity(userRepository.findById(userService.getUserId()).get());
        comment.setTask(task.get());
        commentRepository.save(comment);
        return commentCommentDTOConverter.convert(comment);
    }

    public void deleteComment(int id){
        Optional<Comment> comment=commentRepository.findById(id);
        if(!comment.isPresent()){
            throw new NotFoundException("Comment not found");
        }
        commentRepository.delete(comment.get());
    }


}
