package com.example.Exam.restControllers.candidate;

import com.example.Exam.dtos.CandidateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CandidateControllerInterface {
    @GetMapping
    @CrossOrigin("*")
    List<CandidateDTO> getAllCandidates();

    @GetMapping("/party/{partyId}")
    @CrossOrigin("*")
    List<CandidateDTO> getPartyCandidates(@PathVariable("partyId") int partyId);

    @GetMapping("/{id}")
    @CrossOrigin("*")
    CandidateDTO getCandidateById(@PathVariable("id") int id);

    @PostMapping("/party/{partyId}")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    CandidateDTO createCandidate(@RequestBody CandidateDTO candidate, @PathVariable("partyId") int partyId);

    @PutMapping("/{candidateId}/party/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    CandidateDTO editCandidate(@PathVariable("candidateId") int candidateId, @RequestBody CandidateDTO candidate, @PathVariable("partyId") int partyId);

    @DeleteMapping("/{candidateId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    void deleteCandidate(@PathVariable("candidateId") int candidateId);
}
