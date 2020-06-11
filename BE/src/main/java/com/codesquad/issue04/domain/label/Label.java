package com.codesquad.issue04.domain.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.codesquad.issue04.domain.issue.Issue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(exclude = "issues")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "label_has_issue",
        joinColumns = @JoinColumn(name = "label_id"),
        inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    private Set<Issue> issues;
}
