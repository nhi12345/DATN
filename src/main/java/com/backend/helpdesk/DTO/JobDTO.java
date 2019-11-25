package com.backend.helpdesk.DTO;

import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import lombok.Data;

import java.util.Calendar;

@Data
public class JobDTO {
    private int id;
    private String content;
    private String status;
    private Calendar createAt;
    private Calendar updateAt;
    private UserDTO userCreate;
    private TaskDTO task;
}
