package com.codesquad.issue04.domain.entity;

import com.codesquad.issue04.domain.firstcollections.Issues;
import lombok.*;

import javax.persistence.Embedded;
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
    @Embedded
    private Issues issues;
}
