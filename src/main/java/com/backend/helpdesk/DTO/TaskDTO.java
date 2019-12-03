package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Data
public class TaskDTO {

    private int id;

    private String name;

    private String description;

    private Calendar createAt;

    private Calendar updateAt;

    private Calendar deadline;

    private int idCard;

    private int idUserCreate;
}
