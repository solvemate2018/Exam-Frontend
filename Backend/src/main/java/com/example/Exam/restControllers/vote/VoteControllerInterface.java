package com.example.Exam.restControllers.vote;

import com.example.Exam.dtos.ElectionResults;
import org.springframework.web.bind.annotation.*;

public interface VoteControllerInterface {
    @PostMapping("/{candidateId}")
    @CrossOrigin("*")
    String vote(@RequestBody String cpr, @PathVariable("candidateId") int candidateId);

    @GetMapping("/results")
    @CrossOrigin("*")
    ElectionResults getElectionResults();
}
