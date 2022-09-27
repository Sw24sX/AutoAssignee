package com.example.autoassignee.persistance.properties.choose.assignee.properties;

import com.example.autoassignee.persistance.properties.BaseAutoAssigneePartProperties;
import lombok.Data;

@Data
public class BaseChooseAssigneeProperties extends BaseAutoAssigneePartProperties {
    private double coefficient = 1;
}
