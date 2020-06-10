package com.codesquad.issue04.domain.firstcollections;

import com.codesquad.issue04.domain.entity.Issue;
import lombok.*;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Embeddable
public class Issues {

    private List<Issue> issueList;
    private Long milestoneId;
}
