package com.backend.helpdesk.DTO;

import com.backend.helpdesk.entity.Card;
import com.backend.helpdesk.entity.Comment;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.RoleEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private int id = 0;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private int age;

    private String avatar;

    private Date birthday;

    private boolean gender;

    private String address;

    private Date startingDay;



}
