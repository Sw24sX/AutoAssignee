package com.example.autoassignee.choose.assignee.excluded.assignee;

import com.example.autoassignee.choose.assignee.PartExcludedAssignee;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.persistance.properties.excluded.assignee.properties.ExcludeAuthorProperties;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.stereotype.Component;

/**
 * Стратегия по исключению автора merge request из списка возможных ревьюверов
 */
@Component
@Slf4j
public class ExcludeAuthor extends PartExcludedAssignee {

    public ExcludeAuthor(ExcludeAuthorProperties properties) {
        super(properties);
    }

    @Override
    protected boolean getPartValue(Reviewer reviewer, MergeRequest mergeRequest) {
        log.info("Run ExcludeAuthor for reviewer {}", reviewer.getMemberId());
        return reviewer.getUsername().equals(mergeRequest.getAuthor().getUsername());
    }
}
