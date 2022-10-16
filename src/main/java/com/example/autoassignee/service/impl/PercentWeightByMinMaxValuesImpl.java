package com.example.autoassignee.service.impl;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.service.WeightByNotValues;
import com.example.autoassignee.service.PercentWeightByMinMaxValues;
import com.example.autoassignee.service.ReviewerService;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;


@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class PercentWeightByMinMaxValuesImpl implements PercentWeightByMinMaxValues {
    private final ReviewerService reviewerService;
    private final PercentWeightByMinMaxValues self;

    public PercentWeightByMinMaxValuesImpl(ReviewerService reviewerService, PercentWeightByMinMaxValues self) {
        this.reviewerService = reviewerService;
        this.self = self;
    }

    public Integer getCorrectWeight(WeightByNotValues weightByNotValues, Reviewer reviewer, MergeRequest mergeRequest) {
        int minCount = Integer.MAX_VALUE;
        int maxCount = Integer.MIN_VALUE;
        for (Reviewer activeReviewer : reviewerService.getAllActive()) {
            int count = self.getWeightValueByReviewer(weightByNotValues, activeReviewer, mergeRequest);
            maxCount = Math.max(maxCount, count);
            minCount = Math.min(minCount, count);
        }

        Integer weight = self.getWeightValueByReviewer(weightByNotValues, reviewer, mergeRequest);

        return calculateWeight(minCount, maxCount, weight);
    }

    @Override
    @Cacheable(value = "calculate-weight-reviewer", keyGenerator = "percentWeightKeyGenerator")
    public Integer getWeightValueByReviewer(WeightByNotValues weightByNotValues, Reviewer reviewer, MergeRequest mergeRequest) {
        return weightByNotValues.getPersonalWeight(reviewer, mergeRequest);
    }

    private Integer calculateWeight(Integer minCount, Integer maxCount, Integer current) {
        if (minCount.equals(maxCount)) {

            return 0;
        }

        return ((current - minCount) * 100) / (maxCount - minCount);
    }
}
