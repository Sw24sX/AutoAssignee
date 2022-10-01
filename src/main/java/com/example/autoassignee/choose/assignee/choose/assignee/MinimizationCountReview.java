package com.example.autoassignee.choose.assignee.choose.assignee;

import com.example.autoassignee.choose.assignee.PartChooseAssignee;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.exception.AutoAssigneeException;
import com.example.autoassignee.persistance.properties.choose.assignee.properties.MinimizationCountReviewProperties;
import com.example.autoassignee.service.GitlabApiService;
import com.example.autoassignee.service.ReviewerService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Стратегия минимизации количества назначенных merge request'ов на одного ревьювера.
 * Присваивает вес в 100 ревьюверу с наименьшим количеством merge request'ов и 0 с наибольшим
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MinimizationCountReview extends PartChooseAssignee {

    private final GitlabApiService gitlabApiService;
    private final ReviewerService reviewerService;

    private Integer maxCount = null;
    private Integer minCount = null;

    public MinimizationCountReview(MinimizationCountReviewProperties properties, GitlabApiService gitlabApiService, ReviewerService reviewerService) {
        super(properties);
        this.gitlabApiService = gitlabApiService;
        this.reviewerService = reviewerService;
    }

    @Override
    protected Integer getWeightPart(Reviewer reviewer, MergeRequest mergeRequest) {
        fillMaxAndMinValues();
        Integer currentCount = getCountReview(reviewer.getMemberId());
        return calculateWeight(maxCount, minCount, currentCount);
    }

    private void fillMaxAndMinValues() {
        if (maxCount != null && minCount != null) {

            return;
        }

        this.minCount = Integer.MAX_VALUE;
        this.maxCount = Integer.MIN_VALUE;
        for (Reviewer reviewer : reviewerService.getAllActive()) {
            int count = getCountReview(reviewer.getMemberId());
            this.maxCount = Math.max(maxCount, count);
            this.minCount = Math.min(minCount, count);
        }
    }

    private Integer calculateWeight(Integer min, Integer max, Integer current) {
        if (max.equals(min)) {

            return 0;
        }
        return ((current - min) * 100) / (max - min);
    }

    private Integer getCountReview(Long assigneeId) {
        try {
            return gitlabApiService.getListMergeRequestByAssigneeId(assigneeId, Constants.MergeRequestState.OPENED).size();
        } catch (GitLabApiException e) {
            throw new AutoAssigneeException(e.getMessage());
        }
    }
}
