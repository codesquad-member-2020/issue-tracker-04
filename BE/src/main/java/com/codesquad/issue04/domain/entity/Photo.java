package com.codesquad.issue04.domain.entity;

import javax.persistence.Embeddable;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Embeddable
public class Photo {

    private String url;
    private String githubId;
    private Long commentId;
}
