package com.example.Exam.services.candidate;

import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Party;
import com.example.Exam.repositories.CandidateRepository;
import com.example.Exam.repositories.PartyRepository;
import com.example.Exam.repositories.VoteRepository;
import com.example.Exam.services.party.PartyServiceImpl;
import com.example.Exam.services.vote.VoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CandidateServiceTest {
    @Autowired
    PartyRepository partyRepo;
    @Autowired
    CandidateRepository candidateRepo;
    CandidateServiceImpl candidateService;
    @Autowired
    VoteRepository voteRepo;

    @BeforeEach
    void setUp() {
        candidateService = new CandidateServiceImpl(candidateRepo, partyRepo, voteRepo);
    }


    @Test
    @Sql("/DataSample.sql")
    void getAllCandidates() {
        long size = candidateService.getAllCandidates().size();
        assertEquals(34, size);
    }

    @Test
    @Sql("/DataSample.sql")
    void getPartyCandidates() {
        long size1 = candidateService.getPartyCandidates(1).size();
        assertEquals(8, size1);
        long size2 = candidateService.getPartyCandidates(2).size();
        assertEquals(10, size2);
        long size3 = candidateService.getPartyCandidates(3).size();
        assertEquals(5, size3);
        long size4 = candidateService.getPartyCandidates(4).size();
        assertEquals(1, size4);
    }

    @Test
    @Sql("/DataSample.sql")
    void getCandidateById() {
        Candidate candidate3 = candidateService.getCandidateById(3);
        Candidate candidate5 = candidateService.getCandidateById(5);
        Candidate candidate8 = candidateService.getCandidateById(8);
        Candidate candidate12 = candidateService.getCandidateById(12);
        assertEquals("Helle Hansen", candidate3.getFullName());
        assertEquals("Stefan Hafstein Wolffbrandt", candidate5.getFullName());
        assertEquals("Anders Baun Sørensen", candidate8.getFullName());
        assertEquals("Louise Bramstorp", candidate12.getFullName());
    }

    @Test
    @Sql("/DataSample.sql")
    void createCandidate() {
        Candidate candidate = new Candidate();
        candidate.setFullName("New Candidate");
        candidateService.createCandidate(candidate, 2);
        assertEquals(35, candidateRepo.findAll().size());
        assertEquals(11, candidateRepo.findAllByParty(partyRepo.findById(2).orElseThrow()).size());
        assertEquals("New Candidate", candidateRepo.findAllByParty(partyRepo.findById(2).orElseThrow()).get(10).getFullName());
    }

    @Test
    @Sql("/DataSample.sql")
    void editCandidate() {
        Candidate update = new Candidate();
        update.setFullName("Updated Candidate");

        candidateService.editCandidate(5, update, 4);

        Candidate updatedCandidate = candidateRepo.findById(5).orElseThrow();

        assertEquals("Updated Candidate", updatedCandidate.getFullName());
        assertEquals(4, updatedCandidate.getParty().getId());
    }

    @Test
    @Sql("/DataSample.sql")
    void deleteCandidate() {
        long size = candidateRepo.count();
        candidateService.deleteCandidate(5);
        long sizeAfterwards = candidateRepo.count();

        assertEquals(size - 1, sizeAfterwards);
    }
}