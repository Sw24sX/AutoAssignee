package com.example.autoassignee.choose.assignee;

import com.example.autoassignee.persistance.properties.BaseAutoAssigneePartProperties;

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
     * @param reviewerId reviewerId id ревьювера в бд, для которого будет приниматься решение об исключении
     * @return решение об исключении ревьювера
     */
    public boolean excludeAssignee(Long reviewerId) {
        if (!properties.isEnable()) {
            return false;
        }
        return getPartValue(reviewerId);
    }

    protected abstract boolean getPartValue(Long reviewerId);
}
