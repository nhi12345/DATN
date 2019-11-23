package com.backend.helpdesk.converter.ConvertProject;

import com.backend.helpdesk.DTO.ProjectDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectToProjectDTOConvert extends Converter<Project, ProjectDTO> {

    @Autowired
    private Converter<UserEntity,UserDTO> userEntityUserDTOConverter;

    @Override
    public ProjectDTO convert(Project source) {
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(source.getId());
        projectDTO.setName(source.getName());
        projectDTO.setDescription(source.getDescription());
        projectDTO.setCreateAt(source.getCreateAt());
        projectDTO.setUpdateAt(source.getUpdateAt());
        projectDTO.setUserCreate(userEntityUserDTOConverter.convert(source.getUserCreate()));
        projectDTO.setStatus(source.getStatus().getName());
        return projectDTO;
    }
}
