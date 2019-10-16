package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.CardDTO;
import com.backend.helpdesk.entity.Card;
import com.backend.helpdesk.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @Secured("ROLE_EMPLOYEE")
    @GetMapping
    public List<CardDTO> getAllCard() {
        return cardService.getAllCard();
    }

    @Secured("ROLE_MANAGE")
    @PostMapping("/{id}")
    public Card addCard(@PathVariable("id") int id, @Valid @RequestBody CardDTO cardDTO) {
        return cardService.addCard(id, cardDTO);
    }

    @Secured("ROLE_MANAGE")
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable("id") int id) {
        cardService.deleteCard(id);
    }

    @Secured("ROLE_MANAGE")
    @PutMapping("/{id}")
    public Card editCard(@PathVariable("id") int id, @Valid @RequestBody CardDTO cardDTO) {
        return cardService.editCard(id, cardDTO);
    }
}
