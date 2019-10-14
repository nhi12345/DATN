package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Project {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_card",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
