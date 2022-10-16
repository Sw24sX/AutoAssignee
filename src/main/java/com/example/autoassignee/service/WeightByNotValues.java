package com.example.autoassignee.service;

import com.example.autoassignee.persistance.domain.Reviewer;
import org.gitlab4j.api.models.MergeRequest;

/**
 * Рейализует получение необработанного веса для каждого из ревьюверов.
 * Используется для получения минимального и максимального значений и рассчета веса ревьювера на основе этого
 */
public interface WeightByNotValues {
    Integer getPersonalWeight(Reviewer reviewer, MergeRequest mergeRequest);

    String getCacheKey();
}
