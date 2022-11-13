package com.example.autoassignee.exclude.assignee;

import com.example.autoassignee.choose.assignee.excluded.assignee.ReviewerTaskBranch;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.presets.MergeRequestPreset;
import com.example.autoassignee.presets.ReviewerPreset;
import com.example.autoassignee.repository.HistoryReviewRepository;
import com.example.autoassignee.service.GitlabApiService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ReviewerTaskBranchTests {

    @Autowired
    private ReviewerTaskBranch reviewerTaskBranch;

    @MockBean
    private HistoryReviewRepository historyReviewRepository;

    @Test
    public void ExcludeReviewerNotBranch_Ok() {
        Reviewer first = ReviewerPreset.first();
        MergeRequest request = MergeRequestPreset.first();

        Mockito.when(historyReviewRepository.existsByBranchName("main"))
                .then(x -> true);

        Mockito.when(historyReviewRepository.existsByBranchNameAndReviewer_Id("main", first.getId()))
                .then(x -> true);

        boolean result = reviewerTaskBranch.excludeAssignee(first, request);
        assertThat(result).isTrue();
    }
}
