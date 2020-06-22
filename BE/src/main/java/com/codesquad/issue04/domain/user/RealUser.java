package com.codesquad.issue04.domain.user;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.web.oauth.GithubUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = {"ownedIssues", "assignedIssues"})
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "user")
public class RealUser implements Serializable, AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "github_id")
    private String githubId;
    private String image;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Issue> ownedIssues;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "assignee",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    protected List<Issue> assignedIssues;

    public static RealUser of(GithubUser githubUser) {
        return RealUser.builder()
            .githubId(githubUser.getUserId())
            .name(githubUser.getName())
            .image(githubUser.getImage())
            .assignedIssues(Collections.emptyList())
            .ownedIssues(Collections.emptyList())
            .build();
    }

    public RealUser update(final String name, final String picture) {
        this.name = name;
        this.image = picture;
        return this;
    }

    @Override
    public boolean isNil() {
        return false;
    }

    public boolean hasOwnedIssues() {
        return this.getOwnedIssues().size() > 0;
    }

    public boolean hasAssignedIssues() {
        return this.getAssignedIssues().size() > 0;
    }
}
