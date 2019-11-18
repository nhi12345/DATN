package com.backend.helpdesk.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    private int id;
    private String content;
    private UserDTO userDTO;
}
