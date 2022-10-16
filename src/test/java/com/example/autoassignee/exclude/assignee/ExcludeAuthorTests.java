package com.example.autoassignee.exclude.assignee;

import com.example.autoassignee.choose.assignee.excluded.assignee.ExcludeAuthor;
import com.example.autoassignee.persistance.domain.Reviewer;
import com.example.autoassignee.presets.MergeRequestPreset;
import com.example.autoassignee.presets.ReviewerPreset;
import org.gitlab4j.api.models.Author;
import org.gitlab4j.api.models.MergeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ExcludeAuthorTests {

    @Autowired
    private ExcludeAuthor excludeAuthor;

    @Test
    public void ExcludeAuthor_Ok() {
        MergeRequest mergeRequest = MergeRequestPreset.first();
        Reviewer reviewer = ReviewerPreset.first();

        boolean result = excludeAuthor.excludeAssignee(reviewer, mergeRequest);
        assertThat(result).isTrue();
    }

    @Test
    public void IncludeNotAuthor_Ok() {
        MergeRequest mergeRequest = MergeRequestPreset.first();
        Reviewer reviewer = ReviewerPreset.second();

        boolean result = excludeAuthor.excludeAssignee(reviewer, mergeRequest);
        assertThat(result).isFalse();
    }
}
