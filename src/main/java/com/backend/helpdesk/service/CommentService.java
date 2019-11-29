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

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Converter<Comment, CommentDTO> commentCommentDTOConverter;

    @Autowired
    private Converter<CommentDTO, Comment> commentDTOCommentConverter;

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


    public List<CommentDTO> getCommentByTask(int id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new NotFoundException("Task not found");
        }
        return commentCommentDTOConverter.convert(commentRepository.findByTask(task.get()));
    }
    public CommentDTO addComment(int id, CommentDTO commentDTO) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new NotFoundException("Task not found");
        }
        Calendar calendar = Calendar.getInstance();

        commentDTO.setCreateAt(calendar);
        commentDTO.setUserDTO(userEntityUserDTOConverter.convert(userRepository.findById(userService.getUserId()).get()));
        commentDTO.setTaskDTO(taskTaskDTOConverter.convert(task.get()));
        commentRepository.save(commentDTOCommentConverter.convert(commentDTO));
        commentDTO.setId(commentDTOCommentConverter.convert(commentDTO).getId());
        return commentDTO;
    }

    public List<CommentDTO> deleteComment(int id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new NotFoundException("Comment not found");
        }
        Task task = comment.get().getTask();
        commentRepository.delete(comment.get());
        return commentCommentDTOConverter.convert(commentRepository.findByTask(task));
    }


}
