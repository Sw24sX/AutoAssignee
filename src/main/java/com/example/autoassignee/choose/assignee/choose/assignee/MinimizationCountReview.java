package com.example.autoassignee.choose.assignee.choose.assignee;

import com.example.autoassignee.choose.assignee.PartChooseAssignee;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.dto.PercentWeightByMinMaxSettings;
import com.example.autoassignee.persistance.exception.AutoAssigneeException;
import com.example.autoassignee.persistance.properties.choose.assignee.properties.MinimizationCountReviewProperties;
import com.example.autoassignee.service.PercentWeightByMinMaxValues;
import com.example.autoassignee.service.WeightByNotValues;
import com.example.autoassignee.service.GitlabApiService;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.stereotype.Component;

/**
 * Стратегия минимизации количества назначенных merge request'ов на одного ревьювера.
 * Присваивает вес в 100 ревьюверу с наименьшим количеством merge request'ов и 0 с наибольшим
 */
@Component
@Slf4j
public class MinimizationCountReview extends PartChooseAssignee {

    private final GitlabApiService gitlabApiService;
    private final PercentWeightByMinMaxValues percentWeightByMinMaxValues;

    public MinimizationCountReview(MinimizationCountReviewProperties properties, GitlabApiService gitlabApiService,
                                   PercentWeightByMinMaxValues percentWeightByMinMaxValues) {
        super(properties);
        this.gitlabApiService = gitlabApiService;
        this.percentWeightByMinMaxValues = percentWeightByMinMaxValues;
    }

    @Override
    protected Integer getWeightPart(Reviewer reviewer, MergeRequest mergeRequest) {
        log.info("Run MinimizationCountReview for reviewer {}", reviewer.getUsername());
        PercentWeightByMinMaxSettings settings = PercentWeightByMinMaxSettings.builder()
                .reviewer(reviewer)
                .mergeRequest(mergeRequest)
                .weightByNotValues(new CurrentWeight())
                .isRevert(true)
                .build();
        return percentWeightByMinMaxValues.getCorrectWeight(settings);
    }

    private class CurrentWeight implements WeightByNotValues {

        private static final String CACHE_KEY = "MinimizationCountReview";

        @Override
        public Integer getPersonalWeight(Reviewer reviewer, MergeRequest mergeRequest) {
            try {
                return gitlabApiService.getListMergeRequestByAssigneeId(reviewer.getMemberId(),
                        Constants.MergeRequestState.OPENED).size();
            } catch (GitLabApiException e) {
                throw new AutoAssigneeException(e.getMessage());
            }
        }

        @Override
        public String getCacheKey() {
            return CACHE_KEY;
        }
    }
}
