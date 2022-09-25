package com.example.autoassignee.controller;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.service.MergeRequestService;
import com.example.autoassignee.service.ReviewerService;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviewer")
public class ReviewerController {
    private final ReviewerService reviewerService;
    private final MergeRequestService mergeRequestService;

    public ReviewerController(ReviewerService reviewerService, MergeRequestService mergeRequestService) {
        this.reviewerService = reviewerService;
        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping
    public List<Reviewer> getAll() {
        return reviewerService.getAll();
    }

    @GetMapping("{id}")
    public Reviewer getById(@PathVariable("id") Long id) {

        return reviewerService.getById(id);
    }

    @PostMapping
    public Reviewer addReviewer(@RequestParam String username) throws GitLabApiException {

        return reviewerService.addNewReviewer(username);
    }

    @PutMapping("{id}")
    public Reviewer deleteAccessReviewer(@PathVariable("id") Long id) {
        Reviewer reviewer = reviewerService.getById(id);
        return reviewerService.deleteAccessReviewer(reviewer.getUsername());
    }

    @PostMapping("{reviewer-id}/merge-request/{merge-request-id}")
    public boolean setAssigneeMergeRequest(@PathVariable("reviewer-id") Long reviewerId,
                                           @PathVariable("merge-request-id") Long mergeRequestId)
            throws GitLabApiException {

        Reviewer reviewer = reviewerService.getById(reviewerId);
        mergeRequestService.setAssignee(mergeRequestId, reviewer.getMemberId());
        return true;
    }
}
