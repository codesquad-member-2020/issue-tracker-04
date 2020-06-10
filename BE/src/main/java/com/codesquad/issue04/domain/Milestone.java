package com.codesquad.issue04.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Milestone {

    private Long id;
    private String title;
    private LocalDateTime dueDate;
    private String description;
    private List<Issue> issues;
}
