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
public class JobDTOToJobConvert extends Converter<JobDTO, Job> {

    @Autowired
    private Converter<UserDTO, UserEntity> userDTOUserEntityConverter;

    @Autowired
    private Converter<TaskDTO, Task> taskDTOTaskConverter;

    @Override
    public Job convert(JobDTO source) {
        Job job=new Job();
        job.setId(source.getId());
        job.setContent(source.getContent());
        job.setCreateAt(source.getCreateAt());
        job.setUpdateAt(source.getUpdateAt());
        job.setStatus(source.getStatus());
        job.setUserCreate(userDTOUserEntityConverter.convert(source.getUserCreate()));
        job.setTask(taskDTOTaskConverter.convert(source.getTask()));
        return job;
    }
}
