package com.backend.helpdesk.converter.ConvertJob;

import com.backend.helpdesk.DTO.JobDTO;
import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Job;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobToJobDTOConvert extends Converter<Job, JobDTO> {

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    private Converter<Task, TaskDTO> taskTaskDTOConverter;
    @Override
    public JobDTO convert(Job source) {
        JobDTO jobDTO=new JobDTO();
        jobDTO.setId(source.getId());
        jobDTO.setContent(source.getContent());
        jobDTO.setStatus(source.getStatus());
        jobDTO.setCreateAt(source.getCreateAt());
        jobDTO.setUpdateAt(source.getUpdateAt());
        jobDTO.setUserCreate(userEntityUserDTOConverter.convert(source.getUserCreate()));
        jobDTO.setTask(taskTaskDTOConverter.convert(source.getTask()));
        return jobDTO;
    }
}
