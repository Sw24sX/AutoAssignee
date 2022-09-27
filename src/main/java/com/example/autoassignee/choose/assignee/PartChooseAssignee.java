package com.example.autoassignee.choose.assignee;

import com.example.autoassignee.persistance.properties.choose.assignee.properties.BaseChooseAssigneeProperties;
import org.gitlab4j.api.models.MergeRequest;

/**
 * Часть алгоритма выбора ревьювера
 */
public abstract class PartChooseAssignee {

    private final BaseChooseAssigneeProperties baseChooseAssigneeProperties;

    protected PartChooseAssignee(BaseChooseAssigneeProperties baseChooseAssigneeProperties) {
        this.baseChooseAssigneeProperties = baseChooseAssigneeProperties;
    }

    /**
     * Получить вес для переданного ревьювера
     * Вес - целое число в промежутке от 0 до 100,
     * где 0 - ревьювер совершенно не подходит для ревью из рассчета данной части алгоритма, а 100 - абсолютно подходит
     * @param reviewerId id ревьювера в бд, для которого будет рассчитываться вес
     * @return вес в промежутке от 0 до 100
     */
    public Integer getWeight(Long reviewerId, MergeRequest mergeRequest) {
        if (baseChooseAssigneeProperties.isEnable() || baseChooseAssigneeProperties.getCoefficient() == 0) {
            return 0;
        }
        Double result = getWeightPart(reviewerId, mergeRequest) * baseChooseAssigneeProperties.getCoefficient();

        return result.intValue();
    }

    protected abstract Integer getWeightPart(Long reviewerId, MergeRequest mergeRequest);
}
