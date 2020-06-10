package com.codesquad.issue04.domain;


import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {

    private Long id;
    private String name;
    private String githubId;
    private String image;

}
