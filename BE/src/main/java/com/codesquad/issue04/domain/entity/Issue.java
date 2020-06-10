package com.codesquad.issue04.domain.entity;

import com.codesquad.issue04.domain.firstcollections.Comments;
import com.codesquad.issue04.domain.firstcollections.Labels;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Embedded
    private Comments comments;
    @Embedded
    private Labels labels;
    private String githubId;
}
