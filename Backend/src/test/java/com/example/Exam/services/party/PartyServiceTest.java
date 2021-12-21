package com.example.Exam.services.party;

import com.example.Exam.entities.Party;
import com.example.Exam.repositories.PartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PartyServiceTest {
    @Autowired
    PartyRepository partyRepo;
    PartyServiceImpl partyService;

    @BeforeEach
    void setUp() {
        partyService = new PartyServiceImpl(partyRepo);
    }

    @Test
    @Sql("/DataSample.sql")
    void getAllParties() {
        long size = partyService.getAllParties().size();
        assertEquals(6, size);
    }

    @Test
    @Sql("/DataSample.sql")
    void getPartyById() {
        Party party = partyService.getPartyById(3);
        assertEquals("F - SF, Socialistisk Folkeparti", party.getName());
    }

    @Test
    @Sql("/DataSample.sql")
    void createParty() {
        Party party = partyService.createParty(new Party("Party name"));
        assertEquals(7, partyRepo.findAll().size());
        assertEquals("Party name", partyRepo.findById(partyRepo.findAll().size()).orElseThrow().getName());
    }
}