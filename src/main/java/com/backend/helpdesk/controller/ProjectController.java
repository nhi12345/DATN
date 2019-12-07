package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.ProjectDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.service.ProjectService;
import com.backend.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_MANAGE")
    @GetMapping
    public List<ProjectDTO> getAllProject() {
        return projectService.getAllProject();
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/user/{id}")
    public List<ProjectDTO> getProjectsByUser(@PathVariable("id") int id) {
        if(id== Constants.PERSONAL){
            id=userService.getUserId();
        }
        return projectService.getProjectsByUser(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable("id") int id) {
        return projectService.getProjectById(id);
    }

    @Secured("ROLE_MANAGE")
    @PostMapping
    public ProjectDTO addProject(@Valid @RequestBody ProjectDTO projectDTO) {
        return projectService.addProject(projectDTO);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public Project editProject(@PathVariable("id") int id, @Valid @RequestBody ProjectDTO projectDTO) {
        return projectService.editProject(id, projectDTO);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/reject/{id}")
    public ProjectDTO deleteProject(@PathVariable("id") int id) {
        return projectService.deleteProject(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/accept/{id}")
    public ProjectDTO acceptProject(@PathVariable("id") int id) {
        return projectService.acceptProject(id);
    }

    @Secured("ROLE_MANAGE")
    @PutMapping("/add_user/{id}")
    public ProjectDTO addUserForProject(@PathVariable("id") int id, @RequestParam(value = "email", required = false) String email) {
        return projectService.addUserForProject(id, email);
    }

    @Secured("ROLE_MANAGE")
    @PutMapping("/remove_user/{id}")
    public ProjectDTO  removeUserForProject(@PathVariable("id") int id, @RequestParam(value = "email", required = false) String email) {
        return projectService.removeUserInProject(id, email);
    }

}
