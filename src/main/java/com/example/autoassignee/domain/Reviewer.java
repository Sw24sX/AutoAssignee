package com.example.autoassignee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gitlab4j.api.models.AccessLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reviewer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviewer extends BaseEntity {

    @Column(name = "access_level_gitlab1")
    private AccessLevel accessLevel;

    @Column(name = "username")
    private String username;

    @Column(name = "member_id")
    private String memberId;
}
