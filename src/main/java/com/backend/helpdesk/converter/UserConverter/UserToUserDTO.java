package com.backend.helpdesk.converter.UserConverter;

import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class UserToUserDTO extends Converter<UserEntity, UserDTO> {


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
        userDTO.setStartingDay(source.getStartingDay());
        return userDTO;
    }
}
