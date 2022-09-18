package com.example.autoassignee.controller;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.service.ReviewerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
