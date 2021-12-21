package com.example.Exam.restControllers.candidate;

import com.example.Exam.dtos.CandidateDTO;
import com.example.Exam.dtos.DTOConverter;
import com.example.Exam.entities.Candidate;
import com.example.Exam.services.candidate.CandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidate")
public class CandidateControllerImpl implements CandidateControllerInterface {
    @Autowired
    CandidateServiceImpl candidateService;
    DTOConverter converter;

    @Autowired
    public CandidateControllerImpl(CandidateServiceImpl candidateService, DTOConverter dtoConverter){
        this.candidateService = candidateService;
        this.converter = dtoConverter;
    }

    @Override
    public List<CandidateDTO> getAllCandidates() {
        return candidateService.getAllCandidates()
                .stream().map(candidate -> converter.convertToCandidateDTO(candidate))
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> getPartyCandidates(int partyId) {
        return candidateService.getPartyCandidates(partyId)
                .stream().map(candidate -> converter.convertToCandidateDTO(candidate))
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO getCandidateById(int id) {
        return converter.convertToCandidateDTO(candidateService.getCandidateById(id));
    }

    @Override
    public CandidateDTO createCandidate(CandidateDTO candidate, int partyId) {
        return converter.convertToCandidateDTO(candidateService.createCandidate(converter.convertToCandidate(candidate), partyId));
    }

    @Override
    public CandidateDTO editCandidate(int candidateId, CandidateDTO candidate, int partyId) {
        return converter.convertToCandidateDTO(candidateService.editCandidate(candidateId, converter.convertToCandidate(candidate), partyId));
    }

    @Override
    public void deleteCandidate(int candidateId) {
        candidateService.deleteCandidate(candidateId);
    }
}
