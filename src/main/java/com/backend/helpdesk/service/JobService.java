package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.JobDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Job;
import com.backend.helpdesk.entity.Task;
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
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
        Job job=jobDTOJobConverter.convert(jobDTO);
        job.setStatus(Constants.PENDING);
        job.setUserCreate(userRepository.findById(userService.getUserId()).get());
        job.setTask(task.get());
        jobRepository.save(job);
        return jobJobDTOConverter.convert(job);
    }

    public void deleteJob(int id){
        Optional<Job> job=jobRepository.findById(id);
        if(!job.isPresent()){
            throw new NotFoundException("Job not found");
        }
        jobRepository.delete(job.get());
    }

    public JobDTO acceptJob(int id){
        Optional<Job> job=jobRepository.findById(id);
        if(!job.isPresent()){
            throw new NotFoundException("Job not found");
        }
        job.get().setStatus(Constants.APPROVED);
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
}
