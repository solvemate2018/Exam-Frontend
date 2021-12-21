package com.example.Exam.restControllers.party;

import com.example.Exam.dtos.DTOConverter;
import com.example.Exam.dtos.PartyDTO;
import com.example.Exam.services.candidate.CandidateServiceImpl;
import com.example.Exam.services.party.PartyServiceImpl;
import com.example.Exam.services.party.PartyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/party")
public class PartyControllerImpl implements PartyControllerInterface{
    @Autowired
    PartyServiceImpl partyService;
    DTOConverter converter;

    @Autowired
    public PartyControllerImpl(PartyServiceImpl partyService, DTOConverter dtoConverter){
        this.partyService = partyService;
        this.converter = dtoConverter;
    }

    @Override
    public List<PartyDTO> getAllParties() {
        return partyService.getAllParties()
                .stream().map(party -> converter.convertToPartyDTOWithCandidates(party))
                .collect(Collectors.toList());
    }

    @Override
    public PartyDTO getPartyById(int id) {
        return converter.convertToPartyDTOWithCandidates(partyService.getPartyById(id));
    }

    @Override
    public PartyDTO createParty(PartyDTO party) {
        return converter.convertToPartyDTOWithCandidates(partyService.createParty(converter.convertToParty(party)));
    }
}
