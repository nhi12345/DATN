package com.backend.helpdesk.controller;


import com.backend.helpdesk.DTO.JobDTO;
import com.backend.helpdesk.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/task/{id}")
    public List<JobDTO> getJobByTask(@PathVariable("id")int id) {
        return jobService.getJobByTask(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @PostMapping("/task/{id}")
    public JobDTO addJob(@PathVariable("id") int id, @Valid @RequestBody JobDTO jobDTO){
        return jobService.addJob(jobDTO,id);
    }

    @Secured("ROLE_EMPLOYEES")
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable("id") int id){
        jobService.deleteJob(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @PutMapping("change_status/{id}")
    public JobDTO acceptJob(@PathVariable("id") int id){
        return jobService.changeJob(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @PutMapping("reject/{id}")
    public JobDTO rejectJob(@PathVariable("id") int id){
        return jobService.removeJob(id);
    }

}
