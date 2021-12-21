package com.example.Exam.restControllers.party;

import com.example.Exam.dtos.PartyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PartyControllerInterface {
    @GetMapping
    @CrossOrigin("*")
    List<PartyDTO> getAllParties();

    @GetMapping("/{id}")
    @CrossOrigin("*")
    PartyDTO getPartyById(@PathVariable("id") int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    PartyDTO createParty(@RequestBody PartyDTO party);
}
