package com.codesquad.issue04.domain;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Issue {

    private Long id;
    private String title;
    private List<Comment> comments;
    private List<Label> labels;
    private String githubId;
}
