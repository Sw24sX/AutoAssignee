package com.example.autoassignee.presets;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.presets.dto.ReviewerData;
import org.gitlab4j.api.models.AccessLevel;

public class ReviewerPreset {
    public static Reviewer first() {
        ReviewerData reviewerData = ReviewerDataPreset.first();
        return createByReviewerData(reviewerData);
    }

    public static Reviewer second() {
        ReviewerData reviewerData = ReviewerDataPreset.second();
        return createByReviewerData(reviewerData);
    }

    private static Reviewer createByReviewerData(ReviewerData reviewerData) {
        Reviewer reviewer = new Reviewer();
        reviewer.setId(Long.parseLong(reviewerData.getId()));
        reviewer.setUsername(reviewerData.getUsername());
        reviewer.setGitUsername(reviewerData.getName());
        reviewer.setMemberId(Long.parseLong(reviewerData.getId()));
        reviewer.setAccessLevel(AccessLevel.MAINTAINER);
        return reviewer;
    }
}
