package com.backend.helpdesk.converter.UserConverter;

import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserDTOToUser extends Converter<UserDTO, UserEntity> {

    @Override
    public UserEntity convert(UserDTO source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(source.getId());
        userEntity.setEmail(source.getEmail());
        userEntity.setAddress(source.getAddress());
        userEntity.setAge(source.getAge());
        userEntity.setBirthday(source.getBirthday());
        userEntity.setEmail(source.getEmail());
        userEntity.setFirstName(source.getFirstName());
        userEntity.setLastName(source.getLastName());
        userEntity.setStartingDay(source.getStartingDay());
        return userEntity;
    }
}
