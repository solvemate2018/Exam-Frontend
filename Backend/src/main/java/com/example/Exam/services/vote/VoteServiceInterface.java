package com.example.Exam.services.vote;

import com.example.Exam.dtos.ElectionResults;
import com.example.Exam.entities.Vote;
import org.springframework.security.access.prepost.PreAuthorize;

public interface VoteServiceInterface {
    String vote(String cpr, int candidateId);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ElectionResults getElectionResults();
}
