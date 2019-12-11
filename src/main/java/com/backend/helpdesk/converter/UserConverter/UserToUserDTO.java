package com.backend.helpdesk.converter.UserConverter;

import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class UserToUserDTO extends Converter<UserEntity, UserDTO> {


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO convert(UserEntity source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setAddress(source.getAddress());
        userDTO.setAge(source.getAge());
        userDTO.setBirthday(source.getBirthday());
        userDTO.setEmail(source.getEmail());
        userDTO.setFirstName(source.getFirstName());
        userDTO.setLastName(source.getLastName());
        if(source.getAvatar() != null){
            userDTO.setAvatar(new String(Base64.getEncoder().encode(source.getAvatar())));
        }
        userDTO.setStartingDay(source.getStartingDay());
        if(source.getRoleEntities().contains(roleRepository.findByName(Constants.ADMIN))){
            userDTO.setRole("ADMIN");
        }
        else if(source.getRoleEntities().contains(roleRepository.findByName(Constants.MANAGE))){
            userDTO.setRole("MANAGE");
        }
        else {
            userDTO.setRole("EMPLOYEE");
        }
        return userDTO;

    }
}
