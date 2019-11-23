package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Data
public class ProjectDTO {
    private int id;
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Calendar createAt;

    private Calendar updateAt;

    private UserDTO userCreate;

    private String status;
}
