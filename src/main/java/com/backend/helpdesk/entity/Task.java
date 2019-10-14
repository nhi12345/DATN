package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonAlias("name")
    @Column(nullable = false)
    @NonNull
    private String name;

    @JsonAlias("description")
    @Column(nullable = false)
    @NonNull
    private String description;

    @JsonAlias("create_at")
    @Column(nullable = false)
    @NonNull
    private Calendar createAt;

    @JsonAlias("update_at")
    @Column(nullable = false)
    @NonNull
    private Calendar updateAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_create")
    private UserEntity userCreate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

}
