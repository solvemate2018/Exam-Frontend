package com.example.Exam.dtos;

import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Party;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DTOConverter {
    ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Candidate convertToCandidate(CandidateDTO dto){
        return modelMapper.map(dto, Candidate.class);
    }

    public Party convertToParty(PartyDTO dto){
        return modelMapper.map(dto, Party.class);
    }

    public CandidateDTO convertToCandidateDTO(Candidate candidate){
        CandidateDTO dto =  modelMapper.map(candidate, CandidateDTO.class);
        dto.setPartyName(candidate.getParty().getName());
        return dto;
    }

    public PartyDTO convertToPartyDTOWithCandidates(Party party){
        PartyDTO dto =  modelMapper.map(party, PartyDTO.class);
        dto.setCandidates(party.getCandidates().stream().map(candidate -> convertToCandidateDTO(candidate)).collect(Collectors.toList()));
        return dto;
    }

    public PartyDTO convertToPartyDTOWithoutCandidates(Party party){
        PartyDTO dto =  modelMapper.map(party, PartyDTO.class);
        dto.setCandidates(null);
        return dto;
    }
}
