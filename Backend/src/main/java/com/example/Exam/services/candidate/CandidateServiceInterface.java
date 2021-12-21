package com.example.Exam.services.candidate;

import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Party;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CandidateServiceInterface {
    List<Candidate> getAllCandidates();
    List<Candidate> getPartyCandidates(int partyId);
    Candidate getCandidateById(int id);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Candidate createCandidate(Candidate candidate, int partyId);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Candidate editCandidate(int candidateId, Candidate candidate, int partyId);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    void deleteCandidate(int candidateId);
}
