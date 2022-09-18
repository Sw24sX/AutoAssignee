package com.example.autoassignee.persistance.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "gitlab.api")
@Component
@Data
public class GitlabApiProperties {
    private String url;
    private String token;
    private String projectId;
}
