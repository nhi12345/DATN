package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.CardDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.ConvertCard.CardDTOToCardConvert;
import com.backend.helpdesk.converter.ConvertCard.CardToCardDTOConvert;
import com.backend.helpdesk.entity.Card;
import com.backend.helpdesk.entity.Project;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.CardRepository;
import com.backend.helpdesk.repository.ProjectRepository;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardToCardDTOConvert cardToCardDTOConvert;

    @Autowired
    private CardDTOToCardConvert cardDTOToCardConvert;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<CardDTO> getAllCardByProject(int id) {
        Optional<Project> project=projectRepository.findById(id);
        if(!project.isPresent()){
            throw new NotFoundException("Project not found");
        }
        return cardToCardDTOConvert.convert(cardRepository.findByProject(project.get()));
    }

    public Card addCard(int id,CardDTO cardDTO){
        Optional<Project> project=projectRepository.findById(id);
        if(!project.isPresent()){
            throw new NotFoundException("Project not found");
        }
        Optional<Card> card1=cardRepository.findByName(cardDTO.getName());
        if(card1.isPresent()){
            throw new BadRequestException("Card is existed");
        }
        Project project1=project.get();
        Calendar calendar=Calendar.getInstance();
        cardDTO.setUpdateAt(calendar);
        cardDTO.setCreateAt(calendar);
        cardDTO.setIdUserCreate(userService.getUserId());
        cardDTO.setIdProject(id);
        return cardRepository.save(cardDTOToCardConvert.convert(cardDTO));
    }

    public Card editCard(int id,CardDTO cardDTO){
        Optional<Card> card = cardRepository.findById(id);
        if (!card.isPresent()) {
            throw new NotFoundException("card not found!");
        }
        Optional<Card> card1=cardRepository.findByName(cardDTO.getName());
        if(card1.isPresent()){
            throw new BadRequestException("Card is existed");
        }
        if (card.get().getUserCreate() == null) {
            card.get().setUserCreate(userRepository.findById(userService.getUserId()).get());
        }
        if (card.get().getUserCreate().getId() != userService.getUserId() || !userRepository.findById(card.get().getUserCreate().getId()).get().getRoleEntities().contains(roleRepository.findByName(Constants.MANAGE))) {
            throw new BadRequestException("Not have access");
        }
        Calendar calendar=Calendar.getInstance();
        card.get().setUpdateAt(calendar);
        card.get().setName(cardDTO.getName());
        return card.get();
    }

    public void deleteCard(int id){
        Optional<Card> card = cardRepository.findById(id);
        if (!card.isPresent()) {
            throw new NotFoundException("card not found!");
        }
        cardRepository.deleteById(id);
    }

}
