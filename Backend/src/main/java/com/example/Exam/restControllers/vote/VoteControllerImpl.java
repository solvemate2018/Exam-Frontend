package com.example.Exam.restControllers.vote;

import com.example.Exam.dtos.DTOConverter;
import com.example.Exam.dtos.ElectionResults;
import com.example.Exam.services.vote.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote")
public class VoteControllerImpl implements VoteControllerInterface{
    @Autowired
    VoteServiceImpl voteService;
    DTOConverter converter;

    @Autowired
    public VoteControllerImpl(VoteServiceImpl voteService, DTOConverter dtoConverter){
        this.voteService = voteService;
        this.converter = dtoConverter;
    }

    @Override
    public String vote(String cpr, int candidateId) {
        return voteService.vote(cpr, candidateId);
    }

    @Override
    public ElectionResults getElectionResults() {
        return voteService.getElectionResults();
    }
}
