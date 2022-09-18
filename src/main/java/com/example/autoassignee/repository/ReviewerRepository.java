package com.example.autoassignee.repository;

import com.example.autoassignee.persistance.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
}