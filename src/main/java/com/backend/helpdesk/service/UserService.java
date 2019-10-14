package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.UserNotFoundException;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

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

    public Pageable getPageableSort(String valueSort, int indexPage, int sizeList) {
        if (valueSort.equals("") || valueSort == null) {
            valueSort = "id";
        }
        return PageRequest.of(indexPage, sizeList, Sort.by(valueSort));
    }
}
