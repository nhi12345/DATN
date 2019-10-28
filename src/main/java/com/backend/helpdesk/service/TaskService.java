package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.TaskDTO;
import com.backend.helpdesk.converter.ConvertCard.CardToCardDTOConvert;
import com.backend.helpdesk.converter.ConvertTask.TaskDTOToTaskConvert;
import com.backend.helpdesk.converter.ConvertTask.TaskToTaskDTOConvert;
import com.backend.helpdesk.entity.Card;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.entity.Task;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.CardRepository;
import com.backend.helpdesk.repository.TaskRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TaskToTaskDTOConvert taskToTaskDTOConvert;

    @Autowired
    private TaskDTOToTaskConvert taskDTOToTaskConvert;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public List<TaskDTO> getAllTaskByCard(int id) {
        Optional<Card> card = cardRepository.findById(id);
        if (!card.isPresent()) {
            throw new NotFoundException("Card not found!");
        }
        return taskToTaskDTOConvert.convert(taskRepository.findByCard(card.get()));
    }

    public Task addTask(int id, TaskDTO taskDTO) {
        Optional<Card> card = cardRepository.findById(id);
        if (!card.isPresent()) {
            throw new NotFoundException("Card not found!");
        }
        Optional<Task> task=taskRepository.findByName(taskDTO.getName());
        if(task.isPresent()){
            throw new BadRequestException("task is existed");
        }
        Calendar calendar = Calendar.getInstance();
        taskDTO.setCreateAt(calendar);
        taskDTO.setUpdateAt(calendar);
        taskDTO.setIdUserCreate(userService.getUserId());
        taskDTO.setIdCard(id);
        return taskRepository.save(taskDTOToTaskConvert.convert(taskDTO));
    }

    public Task editTask(int id, TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new NotFoundException("Task not found!");
        }
        Optional<Task> task1=taskRepository.findByName(taskDTO.getName());
        if(task1.isPresent()){
            throw new BadRequestException("task is existed");
        }
        Calendar calendar = Calendar.getInstance();
        task.get().setUpdateAt(calendar);
        task.get().setName(taskDTO.getName());
        task.get().setDescription(taskDTO.getDescription());
        return taskRepository.save(task.get());
    }

    public void deleteTask(int id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new NotFoundException("Task not found!");
        }
        taskRepository.delete(task.get());
    }

    public TaskDTO addUserForTask(int id,String email){
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new NotFoundException("Task not found!");
        }
        Optional<UserEntity> userEntity=userRepository.findByEmail(email);
        if(!userEntity.isPresent()){
            throw new NotFoundException("User not found!");
        }
        Project project=task.get().getCard().getProject();
        if(!userEntity.get().getProjects().contains(project)){
            throw new BadRequestException("User not allow!");
        }
        List<UserEntity> userEntities=new ArrayList<>();
        userEntities.add(userEntity.get());
        task.get().setUserEntities(userEntities);
        taskRepository.save(task.get());
        return taskToTaskDTOConvert.convert(task.get());
    }

}
