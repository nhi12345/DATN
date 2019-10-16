package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Data
public class CardDTO {

    private int id;

    @NotBlank
    private String name;

    private Calendar createAt;

    private Calendar updateAt;

    private int idUserCreate;
}
