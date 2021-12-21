package com.example.Exam.repositories;

import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    List<Candidate> findAllByParty(Party party);
}