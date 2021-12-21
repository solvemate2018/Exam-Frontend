package com.example.Exam.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.regex.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vote {
    @Id
    private String cpr;

    @ManyToOne
    Candidate candidate;

    public Vote(String cpr, Candidate candidate) {
        if(Pattern.matches("[\\d]{10}", cpr)){
            this.cpr = cpr;
            this.candidate = candidate;
        }
        else
            throw new IllegalArgumentException("CPR must contain only digits and must be 10 digits long!");
    }
}
