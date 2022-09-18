package com.example.autoassignee.service.impl;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.repository.ReviewerRepository;
import com.example.autoassignee.service.GitlabApiService;
import com.example.autoassignee.service.ReviewerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    private final GitlabApiService gitlabApiService;
    private final ReviewerRepository reviewerRepository;

    public ReviewerServiceImpl(GitlabApiService gitlabApiService, ReviewerRepository reviewerRepository) {
        this.gitlabApiService = gitlabApiService;
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public List<Reviewer> getAll() {
        return reviewerRepository.findAll();
    }

    @Override
    public Reviewer getById(Long id) {
        return reviewerRepository.findById(id).orElse(null);
    }

    @Override
    public Reviewer addNewReviewer(String username) {
        return null;
    }

    @Override
    public Reviewer deleteAccessReviewer(String username) {
        return null;
    }
}
