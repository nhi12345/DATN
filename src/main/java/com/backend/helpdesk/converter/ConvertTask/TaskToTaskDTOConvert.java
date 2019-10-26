package com.backend.helpdesk.converter.ConvertTask;

import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDTOConvert extends Converter<Task, TaskDTO> {
    @Override
    public TaskDTO convert(Task source) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(source.getId());
        taskDTO.setName(source.getName());
        taskDTO.setDescription(source.getDescription());
        taskDTO.setCreateAt(source.getCreateAt());
        taskDTO.setUpdateAt(source.getUpdateAt());
        taskDTO.setIdCard(source.getCard().getId());
        taskDTO.setIdUserCreate(source.getUserCreate().getId());
        return taskDTO;
    }
}
