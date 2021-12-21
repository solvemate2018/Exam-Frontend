package com.example.Exam.repositories;

import com.example.Exam.entities.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Integer> {
    boolean existsByName(String name);
}