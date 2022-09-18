package com.example.autoassignee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AutoAssigneeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoAssigneeApplication.class, args);
    }

}
