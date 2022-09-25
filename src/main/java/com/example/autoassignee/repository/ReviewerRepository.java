package com.example.autoassignee.repository;

import com.example.autoassignee.persistance.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Optional<Reviewer> findByUsername(String username);

    @Query("select r from Reviewer r where r.isReviewAccess = :reviewAccess")
    List<Reviewer> findAllByReviewAccess(boolean reviewAccess);
}