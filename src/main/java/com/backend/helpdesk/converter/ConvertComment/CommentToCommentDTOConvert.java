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
public class CommentToCommentDTOConvert extends Converter<Comment, CommentDTO> {

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    private Converter <Task, TaskDTO> taskTaskDTOConverter;

    @Override
    public CommentDTO convert(Comment source) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(source.getId());
        commentDTO.setContent(source.getContent());
        commentDTO.setCreateAt(source.getCreateAt());
        commentDTO.setUpdateAt(source.getUpdateAt());
        commentDTO.setUserDTO(userEntityUserDTOConverter.convert(source.getUserEntity()));
        commentDTO.setTaskDTO(taskTaskDTOConverter.convert(source.getTask()));
        return commentDTO;
    }
}
