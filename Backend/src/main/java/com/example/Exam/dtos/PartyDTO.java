package com.example.Exam.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartyDTO {
    private int id;
    private String name;
    private List<CandidateDTO> candidates;
    private int numberOfVotes;
}
