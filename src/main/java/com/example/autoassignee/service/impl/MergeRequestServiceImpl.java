package com.example.autoassignee.service.impl;

import com.example.autoassignee.choose.assignee.FullChooseAssignee;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.exception.AutoAssigneeException;
import com.example.autoassignee.service.GitlabApiService;
import com.example.autoassignee.service.MergeRequestService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MergeRequestServiceImpl implements MergeRequestService {

    private final GitlabApiService gitlabApiService;
    private final FullChooseAssignee fullChooseAssignee;

    public MergeRequestServiceImpl(GitlabApiService gitlabApiService, FullChooseAssignee fullChooseAssignee) {
        this.gitlabApiService = gitlabApiService;
        this.fullChooseAssignee = fullChooseAssignee;
    }

    @Override
    public MergeRequest setAssignee(Long mergeRequestIid, Long assigneeId) {

        try {
            List<Member> projectMembers = gitlabApiService.getListMembers();
            if (projectMembers.stream().noneMatch(member -> Objects.equals(member.getId(), assigneeId))) {
                throw new AutoAssigneeException("Участник c id '%s' не найден в проекте", assigneeId.toString());
            }

            MergeRequest mergeRequest = getOpenMergeRequest(mergeRequestIid);
            gitlabApiService.setAssigneeToMergeRequest(mergeRequestIid, assigneeId);

            return mergeRequest;

        } catch (GitLabApiException e) {
            throw new AutoAssigneeException(e.getMessage());
        }
    }

    @Override
    public MergeRequest setAutoAssignee(Long mergeRequestIid) {

        try {
            MergeRequest mergeRequest = getOpenMergeRequest(mergeRequestIid);
            Reviewer reviewer = fullChooseAssignee.getAssignee(mergeRequest);
            gitlabApiService.setAssigneeToMergeRequest(mergeRequestIid, reviewer.getMemberId());

            return mergeRequest;

        } catch (GitLabApiException e) {
            throw new AutoAssigneeException(e.getMessage());
        }
    }
    
    private MergeRequest getOpenMergeRequest(Long mergeRequestIid) throws GitLabApiException {

        MergeRequest mergeRequest = gitlabApiService.getMergeRequest(mergeRequestIid)
                .orElseThrow(() -> new AutoAssigneeException("Merge request c id '%s' не найден в проекте",
                        mergeRequestIid.toString()));
        if (!mergeRequest.getState().equals(Constants.MergeRequestState.OPENED.toValue())) {
            throw new AutoAssigneeException("Открытый merge request c id '%s' не найден в проекте", mergeRequestIid.toString());
        }
        
        return mergeRequest;
    }
}
