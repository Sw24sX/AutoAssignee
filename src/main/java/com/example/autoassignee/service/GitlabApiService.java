package com.example.autoassignee.service;

import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.MergeRequest;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления api Gitlab
 */
public interface GitlabApiService {

    /**
     * Список участников проекта
     * @return Список участников проекта
     */
    List<Member> getListMembers() throws GitLabApiException;

    /**
     * Список merge request проекта
     * @return Список merge request проекта
     */
    List<MergeRequest> getListMergeRequest() throws GitLabApiException;

    List<MergeRequest> getListMergeRequestByStatus(Constants.MergeRequestState status) throws GitLabApiException;

    Optional<MergeRequest> getMergeRequest(Long iid) throws GitLabApiException;

    /**
     * Назначить ревьювера на merge request
     * @param mergeRequestIid Идентификатор merge request
     * @param memberId Идентификатор назначаемого участника проекта
     * @return Обновленный merge request
     */
    MergeRequest setAssigneeToMergeRequest(Long mergeRequestIid, Long memberId) throws GitLabApiException;
}
