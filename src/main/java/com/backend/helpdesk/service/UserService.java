package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.UserNotFoundException;
import com.backend.helpdesk.repository.ProjectRepository;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.TaskRepository;
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

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

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

    public boolean isManage(int id){
        Optional<UserEntity> userEntityOpt = userRepository.findById(id);
        if (!userEntityOpt.isPresent()) {
            throw new UserNotFoundException();
        }
        if(userEntityOpt.get().getRoleEntities().contains(roleRepository.findByName(Constants.MANAGE))){
            return true;
        }
        return false;
    }

    public boolean isAdmin(int id){
        Optional<UserEntity> userEntityOpt = userRepository.findById(id);
        if (!userEntityOpt.isPresent()) {
            throw new UserNotFoundException();
        }
        if(userEntityOpt.get().getRoleEntities().contains(roleRepository.findByName(Constants.ADMIN))){
            return true;
        }
        return false;
    }

    public List<UserDTO> getUsersByProject(int id){
        Project project=projectRepository.findById(id).get();
        return userEntityUserDTOConverter.convert(project.getUserEntities());
    }

    public List<UserDTO> getUsersByTask(int id){
        Task task=taskRepository.findById(id).get();
        return userEntityUserDTOConverter.convert(task.getUserEntities());
    }

}
