package com.backend.helpdesk.converter.ConvertTask;

import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.repository.CardRepository;
import com.backend.helpdesk.repository.ProjectRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOToTaskConvert extends Converter<TaskDTO, Task> {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task convert(TaskDTO source) {
        Task task = new Task();
        task.setId(source.getId());
        task.setName(source.getName());
        task.setDescription(source.getDescription());
        task.setCreateAt(source.getCreateAt());
        task.setUpdateAt(source.getUpdateAt());
        task.setCard(cardRepository.findById(source.getIdCard()).get());
        task.setUserCreate(userRepository.findById(source.getIdUserCreate()).get());
        return task;
    }
}
