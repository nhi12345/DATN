package com.backend.helpdesk.converter.ConvertProject;

import com.backend.helpdesk.DTO.ProjectDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Project;

public class ProjectToProjectDTOConvert extends Converter<Project, ProjectDTO> {
    @Override
    public ProjectDTO convert(Project source) {
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(source.getId());
        projectDTO.setName(source.getName());
        projectDTO.setDescription(source.getDescription());
        projectDTO.setCreateAt(source.getCreateAt());
        projectDTO.setUpdateAt(source.getUpdateAt());
        projectDTO.setUserIdCreate(source.getUserCreate().getId());
        return projectDTO;
    }
}
