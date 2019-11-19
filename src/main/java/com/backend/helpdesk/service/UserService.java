package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.UserNotFoundException;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    UserRepository userRepository;

    public void setStatusEnableOfUser(int idUser, boolean status) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(idUser);
        if (!userEntityOpt.isPresent()) {
            throw new UserNotFoundException();
        }
        UserEntity userEntity = userEntityOpt.get();
        userEntity.setEnable(status);
        userRepository.save(userEntity);
    }

    public int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findByEmail(email).get();
        return userEntity.getId();
    }

    public List<UserDTO> getAllUser() {
        return userEntityUserDTOConverter.convert(userRepository.findAll());
    }


}
