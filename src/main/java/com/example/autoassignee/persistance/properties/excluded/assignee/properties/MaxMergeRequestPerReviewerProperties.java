package com.example.autoassignee.persistance.properties.excluded.assignee.properties;

import com.example.autoassignee.persistance.properties.BaseAutoAssigneePartProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "auto-assignee.excluded.part.max-merge-request-per-reviewer")
@Data
@Component
public class MaxMergeRequestPerReviewerProperties extends BaseAutoAssigneePartProperties {
    private Integer maxMergeRequests = 4;
}
