package com.backend.helpdesk.converter.ConvertComment;

import com.backend.helpdesk.DTO.CommentDTO;
import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Comment;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDTOToCommentConvert extends Converter<CommentDTO, Comment> {

    @Autowired
    private Converter<UserDTO, UserEntity> userDTOUserEntityConverter;

    @Autowired
    private Converter <TaskDTO, Task> taskDTOTaskConverter;

    @Override
    public Comment convert(CommentDTO source) {
        Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setContent(source.getContent());
        comment.setCreateAt(source.getCreateAt());
        comment.setUserEntity(userDTOUserEntityConverter.convert(source.getUserDTO()));
        comment.setTask(taskDTOTaskConverter.convert(source.getTaskDTO()));
        return comment;
    }
}
