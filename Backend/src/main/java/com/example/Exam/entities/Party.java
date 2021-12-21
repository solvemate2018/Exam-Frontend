package com.example.Exam.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @OneToMany(mappedBy = "party")
    List<Candidate> candidates = new ArrayList<>();

    public Party(String name) {
        this.name = name;
    }
}
