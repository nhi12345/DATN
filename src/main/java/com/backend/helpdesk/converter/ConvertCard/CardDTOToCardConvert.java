package com.backend.helpdesk.converter.ConvertCard;

import com.backend.helpdesk.DTO.CardDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Card;
import com.backend.helpdesk.repository.ProjectRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardDTOToCardConvert extends Converter<CardDTO, Card> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Card convert(CardDTO source) {
        Card card = new Card();
        card.setId(source.getId());
        card.setNameCard(source.getNameCard());
        card.setCreateAt(source.getCreateAt());
        card.setUpdateAt(source.getCreateAt());
        card.setUserCreate(userRepository.findById(source.getIdUserCreate()).get());
        card.setProject(projectRepository.findById(source.getIdProject()).get());
        return card;
    }
}
