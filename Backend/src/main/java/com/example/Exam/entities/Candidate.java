package com.example.Exam.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String fullName;

    @ManyToOne
    @NotNull
    Party party;

    @OneToMany(mappedBy = "candidate")
    List<Vote> votes = new ArrayList<>();

    public Candidate(String fullName, Party party) {
        this.fullName = fullName;
        this.party = party;
    }
}
