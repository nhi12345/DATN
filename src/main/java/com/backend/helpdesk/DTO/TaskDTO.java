package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Data
public class TaskDTO {

    private int id;guCalendar updateAt;

    private int idCard;

    private int idUserCreate;
}
