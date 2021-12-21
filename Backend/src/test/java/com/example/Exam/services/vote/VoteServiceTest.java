package com.example.Exam.services.vote;

import com.example.Exam.dtos.DTOConverter;
import com.example.Exam.entities.Candidate;
import com.example.Exam.repositories.CandidateRepository;
import com.example.Exam.repositories.PartyRepository;
import com.example.Exam.repositories.VoteRepository;
import com.example.Exam.services.candidate.CandidateServiceImpl;
import com.example.Exam.services.party.PartyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VoteServiceTest {
    @Autowired
    PartyRepository partyRepo;
    @Autowired
    CandidateRepository candidateRepo;
    VoteServiceImpl voteService;
    @Autowired
    VoteRepository voteRepo;
    DTOConverter dtoConverter = new DTOConverter(new ModelMapper());

    @BeforeEach
    void setUp() {
        voteService = new VoteServiceImpl(voteRepo, candidateRepo, partyRepo, dtoConverter);
    }

    @Test
    @Sql("/DataSample.sql")
    void vote() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            voteService.vote("16512", 4);
        });
        String expectedMessage = "CPR must contain only digits and must be 10 digits long!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        Candidate candidate = candidateRepo.findById(6).orElseThrow();

        assertEquals("You successfully voted for: " + candidate.getFullName(), voteService.vote("1651245875", 6));
    }
}