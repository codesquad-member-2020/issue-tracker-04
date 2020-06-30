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
public class AssigneeDto {

    private Long id;
    private String name;
    private String githubId;
    private String image;

    public static AssigneeDto of(RealUser realUser) {
        return AssigneeDto.builder()
            .id(realUser.getId())
            .name(realUser.getName())
            .githubId(realUser.getGithubId())
            .image(realUser.getImage())
            .build();
    }
}
