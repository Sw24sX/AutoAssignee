package com.example.autoassignee.choose.assignee;

import org.gitlab4j.api.models.MergeRequest;

/**
 * Часть алгоритма выбора ревьювера
 */
public interface PartChooseAssignee {

    /**
     * Получить вес для переданного ревьювера
     * Вес - целое число в промежутке от 0 до 100,
     * где 0 - ревьювер совершенно не подходит для ревью из рассчета данной части алгоритма, а 100 - абсолютно подходит
     * @param reviewerId id ревьювера в бд, для которого будет рассчитываться вес
     * @return вес в промежутке от 0 до 100
     */
    int getWeight(Long reviewerId, MergeRequest mergeRequest);
}
