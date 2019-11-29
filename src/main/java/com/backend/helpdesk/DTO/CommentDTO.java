package com.backend.helpdesk.DTO;

import lombok.Data;

import java.util.Calendar;

@Data
public class CommentDTO {
    private int id;
    private String content;
    private Calendar createAt;
    private Calendar updateAt;
    private UserDTO userDTO;
    private TaskDTO taskDTO;
}
