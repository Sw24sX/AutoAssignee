package com.example.autoassignee.presets.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewerData {

    private String id;
    private String name;
    private String username;
    private String state;
    private boolean isActive;
    private String email;
}
