package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
    Optional<Card> findByName(String name);
}
