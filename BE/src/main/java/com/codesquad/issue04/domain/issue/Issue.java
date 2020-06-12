package com.codesquad.issue04.domain.issue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "comments")
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "issue", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "label_has_issue",
        joinColumns = @JoinColumn(name = "issue_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "milestone_id"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
    private User user;

    @Builder
    public Issue(Long id, String title, List<Comment> comments,
        Set<Label> labels, Milestone milestone, User user) {

        this.id = id;
        this.title = Optional.ofNullable(title).orElse("직박구리");
        this.comments = Optional.ofNullable(comments).orElse(Collections.emptyList());
        this.labels = Optional.ofNullable(labels).orElse(Collections.emptySet());
        this.milestone = Optional.ofNullable(milestone).orElse(new Milestone());
        this.user = Optional.ofNullable(user).orElse(User.builder().name("존재하지 않는 사용자입니다.").build());
    }


}
