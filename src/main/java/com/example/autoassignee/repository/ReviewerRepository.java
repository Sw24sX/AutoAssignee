package com.example.autoassignee.repository;

import com.example.autoassignee.persistance.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Optional<Reviewer> findByUsername(String username);

    List<Reviewer> findAllByReviewAccess(boolean reviewAccess);
}