package com.example.Exam.services.candidate;

import com.example.Exam.entities.Candidate;
import com.example.Exam.repositories.CandidateRepository;
import com.example.Exam.repositories.PartyRepository;
import com.example.Exam.repositories.VoteRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateServiceInterface{
    private final CandidateRepository candidateRepo;
    private final PartyRepository partyRepo;
    private final VoteRepository voteRepo;

    public CandidateServiceImpl(CandidateRepository candidateRepo, PartyRepository partyRepo, VoteRepository voteRepo) {
        this.candidateRepo = candidateRepo;
        this.partyRepo = partyRepo;
        this.voteRepo = voteRepo;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepo.findAll();
    }

    @Override
    public List<Candidate> getPartyCandidates(int partyId) {
        return candidateRepo.findAllByParty(
                partyRepo.findById(partyId).orElseThrow(
                        () -> new ResourceNotFoundException("There is no party with such ID")));
    }

    @Override
    public Candidate getCandidateById(int id) {
        return candidateRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is not candidate with such ID"));
    }

    @Override
    public Candidate createCandidate(Candidate candidate, int partyId) {
        if(candidate.getFullName().isEmpty() || candidate.getFullName().isBlank() || candidate.getFullName() == null){
            throw new IllegalArgumentException("The name cannot be blank or empty!");
        }
        candidate.setParty(partyRepo.findById(partyId).orElseThrow(() -> new ResourceNotFoundException("There is no party with such ID")));
        return candidateRepo.save(candidate);
    }

    @Override
    public Candidate editCandidate(int candidateId, Candidate candidate, int partyId) {
        Candidate updatedCandidate = candidateRepo.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Candidate with such ID"));

        if(candidate.getFullName() != null || candidate.getFullName() != ""){
            updatedCandidate.setFullName(candidate.getFullName());
        }
        else{
            throw new IllegalArgumentException("The name should not be null. Try including it in the request!");
        }
        if(partyId != updatedCandidate.getParty().getId()){
            updatedCandidate.setParty(partyRepo.findById(partyId).orElseThrow(() -> new ResourceNotFoundException("There is no Party with such ID")));
        }

        return candidateRepo.save(updatedCandidate);
    }

    @Override
    public void deleteCandidate(int candidateId) {
        voteRepo.deleteAllByCandidate(candidateRepo.findById(candidateId).orElseThrow(() -> new ResourceNotFoundException("There is no Candidate with such ID")));
        candidateRepo.deleteById(candidateId);
    }
}
