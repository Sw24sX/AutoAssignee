package com.example.autoassignee.choose.assignee.choose.assignee;

import com.example.autoassignee.choose.assignee.PartChooseAssignee;
import com.example.autoassignee.persistance.properties.choose.assignee.properties.MinimizationCountReviewProperties;
import com.example.autoassignee.service.GitlabApiService;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.stereotype.Component;

/**
 * Стратегия минимизации количества назначенных merge request'ов на одного ревьювера.
 * Присваивает вес в 100 ревьюверу с наименьшим количеством merge request'ов и 0 с наибольшим
 */
@Component
public class MinimizationCountReview extends PartChooseAssignee {

    private final GitlabApiService gitlabApiService;

    public MinimizationCountReview(MinimizationCountReviewProperties properties, GitlabApiService gitlabApiService) {
        super(properties);
        this.gitlabApiService = gitlabApiService;
    }

    @Override
    protected Integer getWeightPart(Long reviewerId, MergeRequest mergeRequest) {
        return null;
    }
}
