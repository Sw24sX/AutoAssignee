package com.example.autoassignee.service;

import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.dto.PercentWeightByMinMaxSettings;
import org.gitlab4j.api.models.MergeRequest;

/**
 * Получение веса от 0 до 100 на основании рассчета минимального и максимального значения.
 */
public interface PercentWeightByMinMaxValues {

    /**
     * Рассчитывает вес ревьювера на основании минимального и максимального значения всех ревьюверов
     * @param settings параметры для рассчета веса ревьювера
     * @return вес ревьювера
     */
    Integer getCorrectWeight(PercentWeightByMinMaxSettings settings);

    /**
     * Рассчитывает вес ревьювера по переданной реализации
     * @param weightByNotValues Реализация рассчета веса для каждого ревьювера
     * @param reviewer ревьвер, для которого необходимо вычислить значение
     * @param mergeRequest обрабатываемый merge request
     * @return вес ревьювера
     */
    Integer getWeightValueByReviewer(WeightByNotValues weightByNotValues, Reviewer reviewer, MergeRequest mergeRequest);
}
