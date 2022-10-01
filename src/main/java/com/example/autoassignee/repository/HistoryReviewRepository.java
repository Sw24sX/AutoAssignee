package com.example.autoassignee.repository;

import com.example.autoassignee.persistance.domain.HistoryReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryReviewRepository  extends JpaRepository<HistoryReview, Long> {
    boolean existsByBranchNameAndReviewer_Id(String branchName, Long reviewer_id);

    boolean existsByBranchName(String branchName);
}
