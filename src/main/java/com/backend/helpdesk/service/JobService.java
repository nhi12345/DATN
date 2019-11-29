package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.JobDTO;
import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Job;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.JobRepository;
import com.backend.helpdesk.repository.TaskRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Converter<Job,JobDTO> jobJobDTOConverter;

    @Autowired
    private Converter<JobDTO,Job> jobDTOJobConverter;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    private Converter<Task, TaskDTO> taskTaskDTOConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public JobDTO getJob(int id){
        Optional<Job> job=jobRepository.findById(id);
        if(!job.isPresent()){
            throw new NotFoundException("Job not found");
        }
        return jobJobDTOConverter.convert(job.get());
    }

    public List<JobDTO> getJobByTask(int id){
        Optional<Task> task=taskRepository.findById(id);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found!");
        }
        return jobJobDTOConverter.convert(jobRepository.findByTask(task.get()));
    }

    public JobDTO addJob(JobDTO jobDTO,int id){
        Optional<Task> task=taskRepository.findById(id);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found!");
        }
        Calendar calendar = Calendar.getInstance();
        jobDTO.setCreateAt(calendar);
        jobDTO.setUpdateAt(calendar);
        jobDTO.setStatus(Constants.PENDING);
        jobDTO.setUserCreate(userEntityUserDTOConverter.convert(userRepository.findById(userService.getUserId()).get()));
        jobDTO.setTask(taskTaskDTOConverter.convert(task.get()));
        jobRepository.save(jobDTOJobConverter.convert(jobDTO));
        jobDTO.setId(jobDTOJobConverter.convert(jobDTO).getId());
        return jobDTO;
    }

    public void deleteJob(int id){
        Optional<Job> job=jobRepository.findById(id);
        if(!job.isPresent()){
            throw new NotFoundException("Job not found");
        }
        jobRepository.delete(job.get());
    }

    public JobDTO changeJob(int id){
        Optional<Job> job=jobRepository.findById(id);
        if(!job.isPresent()){
            throw new NotFoundException("Job not found");
        }
        String status=job.get().getStatus();
        if(status.equals(Constants.PENDING)){
            job.get().setStatus(Constants.APPROVED);
        }else {
            job.get().setStatus(Constants.PENDING);
        }
        jobRepository.save(job.get());
        return jobJobDTOConverter.convert(job.get());
    }

    public JobDTO removeJob(int id){
        Optional<Job> job=jobRepository.findById(id);
        if(!job.isPresent()){
            throw new NotFoundException("Job not found");
        }
        job.get().setStatus(Constants.PENDING);
        jobRepository.save(job.get());
        return jobJobDTOConverter.convert(job.get());
    }

    public int geProcess(int id){
        Optional<Task> task=taskRepository.findById(id);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found!");
        }
        int result=0;
        int jobApproved=jobRepository.findByStatusAndTask(Constants.APPROVED,task.get()).size()*100;
        int jobSum=jobRepository.findByTask(task.get()).size();
        if(jobSum!=0){
            result=jobApproved/jobSum;
        }
        return result;
    }
}
