package com.example.autoassignee.choose.assignee.excluded.assignee;

import com.example.autoassignee.choose.assignee.PartExcludedAssignee;
import com.example.autoassignee.persistance.exception.AutoAssigneeException;
import com.example.autoassignee.persistance.properties.excluded.assignee.properties.MaxMergeRequestPerReviewerProperties;
import com.example.autoassignee.service.GitlabApiService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaxMergeRequestPerReviewer extends PartExcludedAssignee {

    private final GitlabApiService gitlabApiService;
    private final MaxMergeRequestPerReviewerProperties properties;

    public MaxMergeRequestPerReviewer(GitlabApiService gitlabApiService,
                                      MaxMergeRequestPerReviewerProperties properties) {
        super(properties);
        this.gitlabApiService = gitlabApiService;
        this.properties = properties;
    }

    @Override
    protected boolean getPartValue(Long reviewerId) {
        try {
            List<MergeRequest> mergeRequests = gitlabApiService
                    .getListMergeRequestByAssigneeId(reviewerId, Constants.MergeRequestState.OPENED);
            return mergeRequests.size() >= properties.getMaxMergeRequests();
        } catch (GitLabApiException e) {
            throw new AutoAssigneeException(e.getMessage());
        }
    }
}
