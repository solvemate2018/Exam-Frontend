package com.example.Exam.services.party;

import com.example.Exam.entities.Party;
import com.example.Exam.repositories.PartyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PartyServiceImpl implements PartyServiceInterface{
    private final PartyRepository partyRepo;

    public PartyServiceImpl(PartyRepository partyRepo) {
        this.partyRepo = partyRepo;
    }

    @Override
    public List<Party> getAllParties() { return partyRepo.findAll(); }

    @Override
    public Party getPartyById(int id) {
        return partyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no party with such ID"));
    }

    @Override
    public Party createParty(Party party) {
        if(partyRepo.existsByName(party.getName())){
            throw new IllegalArgumentException("A party with name:" + party.getName() + " already exists!");
        }
        return partyRepo.save(party);
    }
}
