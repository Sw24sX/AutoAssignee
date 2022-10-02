package com.example.autoassignee.persistance.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "git.repo")
@Component
@Data
public class GitRepoProperties {
    private String path;
    private String baseBranchName;
}
