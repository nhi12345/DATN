package com.backend.helpdesk.converter.ConvertCard;

import com.backend.helpdesk.DTO.CardDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardToCardDTOConvert extends Converter<Card, CardDTO> {
    @Override
    public CardDTO convert(Card source) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(source.getId());
        cardDTO.setName(source.getName());
        cardDTO.setCreateAt(source.getCreateAt());
        cardDTO.setUpdateAt(source.getUpdateAt());
        cardDTO.setIdUserCreate(source.getUserCreate().getId());
        cardDTO.setIdProject(source.getProject().getId());
        return cardDTO;
    }
}
