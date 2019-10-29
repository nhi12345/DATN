package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@Entity(name = "cards")
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userCreate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "card")
    private List<Task> tasks;

}
