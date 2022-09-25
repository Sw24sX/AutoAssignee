package com.example.autoassignee.service;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;

public interface MergeRequestService {
    MergeRequest setAssignee(Long mergeRequestIid, Long assigneeId);

    MergeRequest setAutoAssignee(Long mergeRequestIid);
}
