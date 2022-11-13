package com.example.autoassignee.exclude.assignee;

import com.example.autoassignee.choose.assignee.excluded.assignee.MaxMergeRequestPerReviewer;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.presets.MergeRequestPreset;
import com.example.autoassignee.presets.ReviewerPreset;
import com.example.autoassignee.service.GitlabApiService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = { "auto-assignee.excluded.part.max-merge-request-per-reviewer.max-merge-requests=2" })
@TestPropertySource(locations="classpath:test.properties")
public class MaxMergeRequestPerReviewerTests {
    @Autowired
    private MaxMergeRequestPerReviewer maxMergeRequestPerReviewer;

    @MockBean
    private GitlabApiService gitlabApiService;

    @BeforeEach
    public void setUp() throws GitLabApiException {
        Reviewer first = ReviewerPreset.first();
        Reviewer second = ReviewerPreset.second();
        List<MergeRequest> firstMergeRequests = List.of(
                MergeRequestPreset.first(),
                MergeRequestPreset.first(),
                MergeRequestPreset.first(),
                MergeRequestPreset.first(),
                MergeRequestPreset.first()
        );

        List<MergeRequest> secondMergeRequests = List.of(
                MergeRequestPreset.first()
        );

        Mockito.when(gitlabApiService.
                getListMergeRequestByAssigneeId(first.getMemberId(), Constants.MergeRequestState.OPENED))
                .then(x -> firstMergeRequests);

        Mockito.when(gitlabApiService.
                        getListMergeRequestByAssigneeId(second.getMemberId(), Constants.MergeRequestState.OPENED))
                .then(x -> secondMergeRequests);
    }

    @Test
    public void ExcludeReviewerWithFiveMR_Ok() {
        Reviewer reviewer = ReviewerPreset.first();
        MergeRequest request = MergeRequestPreset.second();

        assertThat(maxMergeRequestPerReviewer.excludeAssignee(reviewer, request)).isTrue();
    }

    @Test
    public void IncludeReviewerWithOneMR_Ok() {
        Reviewer reviewer = ReviewerPreset.second();
        MergeRequest request = MergeRequestPreset.second();

        assertThat(maxMergeRequestPerReviewer.excludeAssignee(reviewer, request)).isFalse();
    }
}
