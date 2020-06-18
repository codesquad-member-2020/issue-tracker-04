package com.codesquad.issue04.web.dto.response.user;

import com.codesquad.issue04.domain.user.RealUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AuthorDto {

    private Long id;
    private String name;
    private String githubId;
    private String image;

    public static AuthorDto of(RealUser realUser) {
        return AuthorDto.builder()
            .id(realUser.getId())
            .name(realUser.getName())
            .githubId(realUser.getGithubId())
            .image(realUser.getImage())
            .build();
    }
}
