package com.example.autoassignee.choose.assignee;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.exception.AutoAssigneeException;
import com.example.autoassignee.service.ReviewerService;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FullChooseAssignee {
    private final List<? extends PartChooseAssignee> partsChooseAssignees;
    private final List<? extends PartExcludedAssignee> partsExcludedAssignee;
    private final ReviewerService reviewerService;

    public FullChooseAssignee(List<? extends PartChooseAssignee> partsChooseAssignees,
                              List<? extends PartExcludedAssignee> partsExcludedAssignee,
                              ReviewerService reviewerService) {
        this.partsChooseAssignees = partsChooseAssignees;
        this.partsExcludedAssignee = partsExcludedAssignee;
        this.reviewerService = reviewerService;
    }

    public Reviewer getAssignee(MergeRequest mergeRequest) {
        List<Reviewer> reviewers = excludeReviewers(reviewerService.getAllActive(), mergeRequest);
        if (reviewers.size() <= 1) {
            return reviewers.stream()
                    .findFirst()
                    .orElseThrow(() -> new AutoAssigneeException(
                        "Не удалось найти ни одного подходящего ревьювера для merge request с iid = %s",
                        mergeRequest.getIid().toString()));
        }

        Optional<Candidate> candidate = reviewers.stream()
                .map(reviewer -> new Candidate(reviewer, getWeight(reviewer, mergeRequest)))
                .max(Candidate::compareTo);

        return candidate
                .orElseThrow(() -> new AutoAssigneeException("Для merge request с iid '%s' удалось найти ни одного ревьювера",
                        mergeRequest.getIid().toString()))
                .getReviewer();
    }

    private List<Reviewer> excludeReviewers(List<Reviewer> reviewers, MergeRequest mergeRequest) {

        return reviewers.stream()
                .filter(x -> !isReviewerExcluded(x, mergeRequest))
                .toList();
    }

    private boolean isReviewerExcluded(Reviewer reviewer, MergeRequest mergeRequest) {

        return partsExcludedAssignee.stream().anyMatch(x -> x.excludeAssignee(reviewer, mergeRequest));
    }

    private long getWeight(Reviewer reviewer, MergeRequest mergeRequest) {

        return partsChooseAssignees.stream()
                .mapToLong(x -> x.getWeight(reviewer, mergeRequest))
                .sum();
    }
}
