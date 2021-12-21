package com.example.Exam.services.vote;

import com.example.Exam.dtos.CandidateDTO;
import com.example.Exam.dtos.DTOConverter;
import com.example.Exam.dtos.ElectionResults;
import com.example.Exam.dtos.PartyDTO;
import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Party;
import com.example.Exam.entities.Vote;
import com.example.Exam.repositories.CandidateRepository;
import com.example.Exam.repositories.PartyRepository;
import com.example.Exam.repositories.VoteRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteServiceInterface{
    private final VoteRepository voteRepo;
    private final CandidateRepository candidateRepo;
    private final PartyRepository partyRepo;

    private final DTOConverter dtoConverter;

    public VoteServiceImpl(VoteRepository voteRepo, CandidateRepository candidateRepo, PartyRepository partyRepo, DTOConverter converter) {
        this.voteRepo = voteRepo;
        this.candidateRepo = candidateRepo;
        this.partyRepo = partyRepo;
        this.dtoConverter = converter;
    }

    @Override
    public String vote(String cpr, int candidateId) {
        boolean voted = false;
        if(voteRepo.existsById(cpr)){
            voted = true;
        }
        Vote vote = new Vote(cpr, candidateRepo.findById(candidateId).orElseThrow(() -> new ResourceNotFoundException("There is no candidate with such ID")));
        if(voted)
            return "You successfully changed your vote for: " + voteRepo.save(vote).getCandidate().getFullName();
        return "You successfully voted for: " + voteRepo.save(vote).getCandidate().getFullName();
    }

    @Override
    public ElectionResults getElectionResults() {
        ElectionResults results = new ElectionResults();
        int totalNumberOfVotes = 0;

        for (Candidate candidate:
                candidateRepo.findAll()) {
            CandidateDTO dto = dtoConverter.convertToCandidateDTO(candidate);
            dto.setNumberOfVotes(candidate.getVotes().size());
            results.getCandidates().add(dto);
            totalNumberOfVotes += candidate.getVotes().size();
        }

        for (Party party:
                partyRepo.findAll()) {
            PartyDTO dto = dtoConverter.convertToPartyDTOWithoutCandidates(party);
            int votes = 0;
            for (Candidate candidate: party.getCandidates()) {
                votes += candidate.getVotes().size();
            }
            dto.setNumberOfVotes(votes);
            results.getParties().add(dto);
        }
        results.setTotalNumberOfVotes(totalNumberOfVotes);
        return results;
    }

}
