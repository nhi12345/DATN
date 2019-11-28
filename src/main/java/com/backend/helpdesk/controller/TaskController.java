package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") int id) {
        return taskService.getTask(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/card/{id}")
    public List<TaskDTO> getAllTaskByCard(@PathVariable("id") int id) {
        return taskService.getAllTaskByCard(id);
    }

    @Secured("ROLE_MANAGE")
    @PostMapping("/{id}")
    public Task addTask(@PathVariable("id") int id, @Valid @RequestBody TaskDTO taskDTO) {
        return taskService.addTask(id, taskDTO);
    }

    @Secured("ROLE_MANAGE")
    @PutMapping("/id")
    public Task editTask(@PathVariable("id") int id, @Valid @RequestBody TaskDTO taskDTO) {
        return taskService.editTask(id, taskDTO);
    }
    @Secured("ROLE_MANAGE")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        taskService.deleteTask(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @PutMapping("/users/{id}")
    public TaskDTO addUserForTask(@PathVariable("id") int id, @RequestParam(value = "email", required = false) String email) {
        return taskService.addUserForTask(id, email);
    }

    @Secured("ROLE_EMPLOYEES")
    @PutMapping("/replace_task/{idOld}/new_task/{idNew}/task/{id}")
    public TaskDTO replaceTask(@PathVariable("idOld") int idOldCard,@PathVariable("idNew") int idNewCard,@PathVariable("id") int idTask){
        return taskService.replaceTask(idOldCard,idNewCard,idTask);
    }
    @Secured("ROLE_EMPLOYEES")
    @DeleteMapping("/{id}")
    public List<TaskDTO> deleteTask(@PathVariable("id") int id){
        return taskService.deleteTask(id);
    }
}
