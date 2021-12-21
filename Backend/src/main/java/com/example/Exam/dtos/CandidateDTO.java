package com.example.Exam.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateDTO {
    private int id;
    private String fullName;
    private String partyName;
    private int numberOfVotes;
}
