package com.example.autoassignee.service.impl;

import com.example.autoassignee.persistance.properties.GitlabApiProperties;
import com.example.autoassignee.repository.ReviewerRepository;
import com.example.autoassignee.service.GitlabApiService;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestParams;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GitlabApiServiceImpl implements GitlabApiService {

    private final GitlabApiProperties gitlabApiProperties;
    private final GitLabApi gitLabApi;

    public GitlabApiServiceImpl(GitlabApiProperties gitlabApiProperties, ReviewerRepository reviewerRepository) {

        this.gitlabApiProperties = gitlabApiProperties;
        this.gitLabApi = new GitLabApi(gitlabApiProperties.getUrl(), gitlabApiProperties.getToken());
    }

    @Override
    public List<Member> getListMembers() throws GitLabApiException {
        return gitLabApi.getProjectApi().getMembers(gitlabApiProperties.getProjectId());
    }

    @Override
    public List<MergeRequest> getListMergeRequest() throws GitLabApiException {
        return gitLabApi.getMergeRequestApi()
                .getMergeRequests(gitlabApiProperties.getProjectId(), Constants.MergeRequestState.OPENED);
    }

    @Override
    public List<MergeRequest> getListMergeRequestByStatus(Constants.MergeRequestState status) throws GitLabApiException {
        return gitLabApi.getMergeRequestApi()
                .getMergeRequests(gitlabApiProperties.getProjectId(), status);
    }

    @Override
    public MergeRequest getMergeRequest(Long iid) throws GitLabApiException {
        return gitLabApi.getMergeRequestApi()
                .getMergeRequest(gitlabApiProperties.getProjectId(), iid);
    }


    @Override
    public MergeRequest setAssigneeToMergeRequest(Long mergeRequestIid, Long memberId) throws GitLabApiException {
        MergeRequestParams newParams = new MergeRequestParams();
        newParams.withAssigneeId(memberId);
        return gitLabApi.getMergeRequestApi()
                .updateMergeRequest(gitlabApiProperties.getProjectId(), mergeRequestIid, newParams);
    }
}
