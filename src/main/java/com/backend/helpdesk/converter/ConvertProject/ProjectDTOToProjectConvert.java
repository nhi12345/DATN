package com.backend.helpdesk.converter.ConvertProject;

import com.backend.helpdesk.DTO.ProjectDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOToProjectConvert extends Converter<ProjectDTO, Project> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Project convert(ProjectDTO source) {
        Project project = new Project();
        project.setId(source.getId());
        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setCreateAt(source.getCreateAt());
        project.setUpdateAt(source.getUpdateAt());
        project.setUserCreate(userRepository.findById(source.getUserIdCreate()).get());
        project.setStatus(statusRepository.findByName(source.getStatus()));
        return project;
    }
}
