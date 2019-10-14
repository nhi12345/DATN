package com.backend.helpdesk.converter.ConvertProject;

import com.backend.helpdesk.DTO.ProjectDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectDTOToProjectConvert extends Converter<ProjectDTO, Project> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Project convert(ProjectDTO source) {
        Project project=new Project();
        project.setId(source.getId());
        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setCreateAt(source.getCreateAt());
        project.setUpdateAt(source.getUpdateAt());
        project.setUserCreate(userRepository.findById(source.getId()).get());
        return null;
    }
}
