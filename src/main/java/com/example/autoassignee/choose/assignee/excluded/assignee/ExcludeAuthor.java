package com.example.autoassignee.choose.assignee.excluded.assignee;

import com.example.autoassignee.choose.assignee.PartExcludedAssignee;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.properties.excluded.assignee.properties.ExcludeAuthorProperties;
import org.gitlab4j.api.models.MergeRequest;

/**
 * Стратегия по исключению автора merge request из списка возможных ревьюверов
 */
public class ExcludeAuthor extends PartExcludedAssignee {

    public ExcludeAuthor(ExcludeAuthorProperties properties) {
        super(properties);
    }

    @Override
    protected boolean getPartValue(Reviewer reviewer, MergeRequest mergeRequest) {
        return reviewer.getUsername().equals(mergeRequest.getAuthor().getUsername());
    }
}
