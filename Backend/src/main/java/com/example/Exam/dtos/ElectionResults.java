package com.example.Exam.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElectionResults {
    private List<PartyDTO> parties = new ArrayList<>();
    private List<CandidateDTO> candidates = new ArrayList<>();
    private int totalNumberOfVotes;
}
