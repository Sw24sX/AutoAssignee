package com.example.autoassignee.presets;

import com.example.autoassignee.presets.dto.ReviewerData;

public class ReviewerDataPreset {
    public static ReviewerData first() {
        return ReviewerData
                .builder()
                .id("1")
                .isActive(true)
                .name("Test test")
                .username("test_1")
                .email("test_1@mail.ru")
                .build();
    }

    public static ReviewerData second() {
        return ReviewerData
                .builder()
                .id("2")
                .isActive(true)
                .name("No_Test no_test")
                .username("test_2")
                .email("test_2@mail.ru")
                .build();
    }

    public static ReviewerData inactive() {
        return ReviewerData
                .builder()
                .id("3")
                .isActive(false)
                .name("inactive")
                .username("inactive")
                .build();
    }
}
