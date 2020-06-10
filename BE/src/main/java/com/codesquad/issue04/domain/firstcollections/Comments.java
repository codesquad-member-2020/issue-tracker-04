package com.codesquad.issue04.domain.firstcollections;

import com.codesquad.issue04.domain.entity.Comment;
import lombok.*;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Embeddable
public class Comments {

    private List<Comment> commentList;
    private Long issueId;
}
