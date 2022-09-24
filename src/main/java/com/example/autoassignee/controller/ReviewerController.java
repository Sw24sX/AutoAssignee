package com.example.autoassignee.controller;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.service.ReviewerService;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviewer")
public class ReviewerController {
    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
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

    @PutMapping
    public Reviewer deleteAccessReviewer(@RequestParam String username) {
        return reviewerService.deleteAccessReviewer(username);
    }
}
