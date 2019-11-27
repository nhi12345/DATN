package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.ProjectDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.ConvertProject.ProjectDTOToProjectConvert;
import com.backend.helpdesk.converter.ConvertProject.ProjectToProjectDTOConvert;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.exception.UserException.UserAccessDeniedException;
import com.backend.helpdesk.repository.ProjectRepository;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectToProjectDTOConvert projectToProjectDTOConvert;

    @Autowired
    private ProjectDTOToProjectConvert projectDTOToProjectConvert;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    public List<ProjectDTO> getAllProject() {
//        Optional<Status> status = statusRepository.findByName(Constants.APPROVED);
        return projectToProjectDTOConvert.convert(projectRepository.findAll());
    }

    public List<ProjectDTO> getProjectsByUser(int id){
        Optional<UserEntity> userEntity=userRepository.findById(id);
        if(!userEntity.isPresent()){
            throw new NotFoundException("User not found!");
        }
        int a=userEntity.get().getProjects().size();
        int c=0;
        return projectToProjectDTOConvert.convert(userEntity.get().getProjects());
    }

    public ProjectDTO getProjectById(int id){
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("project not found!");
        }
        return projectToProjectDTOConvert.convert(project.get());
    }
//huhuh
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        Calendar calendar = Calendar.getInstance();
        projectDTO.setCreateAt(calendar);
        projectDTO.setUpdateAt(calendar);
        projectDTO.setStatus(Constants.PENDING);
        UserEntity userEntity=userRepository.findById(userService.getUserId()).get();
        projectDTO.setUserCreate(userEntityUserDTOConverter.convert(userEntity));
        Project project = projectDTOToProjectConvert.convert(projectDTO);
        userEntity.getProjects().add(project);
        userRepository.save(userEntity);
        return projectToProjectDTOConvert.convert(project);
    }

    public Project editProject(int id, ProjectDTO projectDTO) {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("project not found!");
        }
        if (project.get().getUserCreate() == null) {
            project.get().setUserCreate(userRepository.findById(userService.getUserId()).get());
        }
        if (project.get().getUserCreate().getId() != userService.getUserId() || !userRepository.findById(project.get().getUserCreate().getId()).get().getRoleEntities().contains(roleRepository.findByName(Constants.ADMIN))) {
            throw new BadRequestException("Not have access");
        }
        Calendar calendar = Calendar.getInstance();
        project.get().setName(projectDTO.getName());
        project.get().setDescription(projectDTO.getDescription());
        project.get().setUpdateAt(calendar);
        project.get().setStatus(statusRepository.findByName(Constants.PENDING).get());
        return projectRepository.save(project.get());
    }

    public ProjectDTO deleteProject(int id) {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("project not found!");
        }
        project.get().setStatus(statusRepository.findByName(Constants.REJECTED).get());
        return projectToProjectDTOConvert.convert(projectRepository.save(project.get()));
    }

    public ProjectDTO acceptProject(int id) {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("project not found!");
        }
        project.get().setStatus(statusRepository.findByName(Constants.APPROVED).get());
        return projectToProjectDTOConvert.convert(projectRepository.save(project.get()));
    }

    public ProjectDTO addUserForProject(int id, String email) {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("project not found!");
        }
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(!userEntity.isPresent()){
            throw new NotFoundException("User not found!");
        }
        List<Project> projects = userEntity.get().getProjects();
        if(projects.contains(project.get())) {
            throw new BadRequestException("User is existed!");
        }else {
            userEntity.get().getProjects().add(project.get());
        }
//        userEntity.get().setProjects(projects);
        userRepository.save(userEntity.get());
        return projectToProjectDTOConvert.convert(project.get());
    }

    public ProjectDTO removeUserInProject(int id, String email) {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new NotFoundException("project not found!");
        }
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (!userEntity.isPresent()) {
            throw new NotFoundException("User not found!");
        }
        List<Project> projects = userEntity.get().getProjects();
        if (projects.contains(project.get())) {
            userEntity.get().getProjects().remove(project.get());
        } else {
            throw new BadRequestException("User isn't existed!");
        }
        userRepository.save(userEntity.get());
        return projectToProjectDTOConvert.convert(project.get());
    }

}

