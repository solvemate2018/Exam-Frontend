package com.example.Exam.services.party;

import com.example.Exam.entities.Party;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PartyServiceInterface {
    List<Party> getAllParties();
    Party getPartyById(int id);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Party createParty(Party party);
}
