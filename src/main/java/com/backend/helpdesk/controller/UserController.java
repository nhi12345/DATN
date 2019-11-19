package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping()
    public List<UserDTO> getUserFollowId() {
        return userService.getAllUser();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/enable")
    public void enableUser(@RequestParam int idUser) {
        userService.setStatusEnableOfUser(idUser, true);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/disable")
    public void disableUser(@RequestParam int idUser) {
        userService.setStatusEnableOfUser(idUser, false);
    }

}
