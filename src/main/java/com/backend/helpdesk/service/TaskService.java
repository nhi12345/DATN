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
import com.backend.helpdesk.repository.*;
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

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JobRepository jobRepository;

    public TaskDTO getTask(int id){
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new NotFoundException("Task not found!");
        }
        return taskToTaskDTOConvert.convert(task.get());
    }

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
//        task.get().setName(taskDTO.getName());
        task.get().setDescription(taskDTO.getDescription());
        return taskRepository.save(task.get());
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

    public TaskDTO replaceTask(int idOldCard,int idNewCard,int idTask){
        Optional<Task> task=taskRepository.findById(idTask);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found");
        }
        Optional<Card> oldCard=cardRepository.findById(idOldCard);
        if(!oldCard.isPresent()){
            throw new NotFoundException("Card old not found!");
        }
        Optional<Card> newCard=cardRepository.findById(idNewCard);
        if(!newCard.isPresent()){
            throw new NotFoundException("Card new not found!");
        }
        oldCard.get().getTasks().remove(task.get());
        cardRepository.save(oldCard.get());
        newCard.get().getTasks().add(task.get());
        cardRepository.save(newCard.get());
        task.get().setCard(newCard.get());
        return taskToTaskDTOConvert.convert(taskRepository.save(task.get()));
    }

    public List<TaskDTO> deleteTask(int id){
        Optional<Task> task=taskRepository.findById(id);
        if(!task.isPresent()){
            throw new NotFoundException("Task not found");
        }
        Card card=task.get().getCard();
        jobRepository.deleteInBatch(jobRepository.findByTask(task.get()));
        commentRepository.deleteInBatch(commentRepository.findByTask(task.get()));
        taskRepository.delete(task.get());
        return taskToTaskDTOConvert.convert(taskRepository.findByCard(card));
    }
}
