package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonAlias("name")
    @Column(nullable = false)
    @NonNull
    private String name;

    @JsonAlias("create_at")
    @Column(nullable = false)
    @NonNull
    private Calendar createAt;

    @JsonAlias("update_at")
    @Column(nullable = false)
    @NonNull
    private Calendar updateAt;

    @OneToMany(mappedBy = "card")
    private List<Task> tasks;

}
