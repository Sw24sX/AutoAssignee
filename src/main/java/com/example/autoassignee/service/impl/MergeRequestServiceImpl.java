package com.example.autoassignee.service.impl;

import com.example.autoassignee.persistance.exception.AutoAssigneeException;
import com.example.autoassignee.service.GitlabApiService;
import com.example.autoassignee.service.MergeRequestService;
import com.example.autoassignee.service.ReviewerService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.MergeRequest;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MergeRequestServiceImpl implements MergeRequestService {

    private final GitlabApiService gitlabApiService;

    public MergeRequestServiceImpl(GitlabApiService gitlabApiService) {
        this.gitlabApiService = gitlabApiService;
    }

    @Override
    public MergeRequest setAssignee(Long mergeRequestIid, Long assigneeId) {
        try {
            List<Member> projectMembers = gitlabApiService.getListMembers();
            if (projectMembers.stream().anyMatch(member -> Objects.equals(member.getId(), assigneeId))) {
                throw new AutoAssigneeException("Участник c id '%s' не найден в проекте", assigneeId.toString());
            }

            MergeRequest mergeRequest = gitlabApiService.getMergeRequest(mergeRequestIid);
            if (mergeRequest == null || !mergeRequest.getState().equals(Constants.MergeRequestState.OPENED.toValue())) {
                throw new AutoAssigneeException("Merge request c id '%s' не найден в проекте", mergeRequestIid.toString());
            }

            gitlabApiService.setAssigneeToMergeRequest(mergeRequestIid, assigneeId);
            return mergeRequest;

        } catch (GitLabApiException e) {
            throw new AutoAssigneeException(e.getMessage());
        }
    }
}
