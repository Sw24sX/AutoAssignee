package com.example.autoassignee.choose.assignee;

/**
 * Часть алгоритма по исключению ревьюверов из исписка возможных к назначению
 */
public interface PartExcludedAssignee {

    /**
     * Метод, определяющий необходимость исключить ревьюверо из списка возможных к назначению
     * где true - необходимо исключить из списка назначемых
     * @param reviewerId reviewerId id ревьювера в бд, для которого будет приниматься решение об исключении
     * @return решение об исключении ревьювера
     */
    boolean excludeAssignee(Long reviewerId);
}
