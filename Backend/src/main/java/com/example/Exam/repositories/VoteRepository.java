package com.example.Exam.repositories;

import com.example.Exam.entities.Candidate;
import com.example.Exam.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, String> {
    void deleteAllByCandidate(Candidate candidate);
}