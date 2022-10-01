package com.example.autoassignee.choose.assignee;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.properties.BaseAutoAssigneePartProperties;
import org.gitlab4j.api.models.MergeRequest;

/**
 * Часть алгоритма по исключению ревьюверов из исписка возможных к назначению
 */
public abstract class PartExcludedAssignee {

    private final BaseAutoAssigneePartProperties properties;
    public PartExcludedAssignee(BaseAutoAssigneePartProperties properties) {
        this.properties = properties;
    }

    /**
     * Метод, определяющий необходимость исключить ревьюверо из списка возможных к назначению
     * где true - необходимо исключить из списка назначемых
     * @param reviewer ревьювер, для которого будет приниматься решение об исключении
     * @return решение об исключении ревьювера
     */
    public boolean excludeAssignee(Reviewer reviewer, MergeRequest mergeRequest) {
        if (!properties.isEnable()) {

            return false;
        }

        return getPartValue(reviewer, mergeRequest);
    }

    protected abstract boolean getPartValue(Reviewer reviewer, MergeRequest mergeRequest);
}
