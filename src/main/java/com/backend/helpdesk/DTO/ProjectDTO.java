package com.backend.helpdesk.DTO;

import com.backend.helpdesk.entity.UserEntity;
import lombok.Data;

import java.util.Calendar;

@Data
public class ProjectDTO {
    private int id;
    private String name;
    private String description;
    private Calendar createAt;
    private Calendar updateAt;
    private int userIdCreate;
}
