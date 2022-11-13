package com.example.autoassignee.presets;

import com.example.autoassignee.presets.dto.ReviewerData;
import org.gitlab4j.api.models.Author;
import org.gitlab4j.api.models.MergeRequest;

public class MergeRequestPreset {
    public static MergeRequest first() {
        ReviewerData reviewerData = ReviewerDataPreset.first();

        MergeRequest request = new MergeRequest();
        request.setAuthor(createAuthor(reviewerData));
        request.setId(1L);
        request.setIid(1L);
        request.setState("OPEN");
        request.setProjectId(1L);
        request.setTitle("Test merge request first");
        request.setSourceBranch("test_1");
        request.setTargetBranch("main");
        return request;
    }

    public static MergeRequest second() {
        ReviewerData reviewerData = ReviewerDataPreset.second();

        MergeRequest request = new MergeRequest();
        request.setAuthor(createAuthor(reviewerData));
        request.setId(2L);
        request.setIid(2L);
        request.setState("OPEN");
        request.setProjectId(1L);
        request.setTitle("Test merge request second");
        request.setSourceBranch("test_1");
        request.setTargetBranch("main");
        return request;
    }

    private static Author createAuthor(ReviewerData reviewerData) {
        Author author = new Author();
        author.setUsername(reviewerData.getUsername());
        author.setEmail(reviewerData.getEmail());
        author.setName(reviewerData.getName());
        author.setId(Long.parseLong(reviewerData.getId()));
        return author;
    }
}
