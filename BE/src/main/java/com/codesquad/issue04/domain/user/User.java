package com.codesquad.issue04.domain.user;


import java.io.Serializable;
import java.util.List;

import com.codesquad.issue04.domain.issue.Issue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NaturalId;

@Getter
@ToString(exclude = "issues")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "github_id")
    private String githubId;
    private String image;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Issue> issues;
}
