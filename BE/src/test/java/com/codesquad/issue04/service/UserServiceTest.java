package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.web.dto.response.user.AllAuthorsResponseDto;
import com.codesquad.issue04.web.dto.response.user.AuthorDto;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Transactional
    @DisplayName("이슈를 작성한 사용자 전체를 가져온다.")
    @Test
    void 전체_작성자를_가져온다() {
        AllAuthorsResponseDto allAuthorsResponseDto = userService.getAllAuthors();

        assertThat(allAuthorsResponseDto.getAllData().get(0)).isInstanceOf(AuthorDto.class);
        assertThat(allAuthorsResponseDto.getAllData().get(0).getId()).isEqualTo(1L);
    }

    @Transactional
    @DisplayName("이슈가 할당된 전체 유저를 가져온다.")
    @Test
    void 이슈가_할당된_전체유저를_가져온다() {
        List<RealUser> usersHasIssues = userService.findUsersHasIssues();

        assertThat(usersHasIssues.get(0)).isInstanceOf(RealUser.class);
        assertThat(usersHasIssues.get(0).getGithubId()).isEqualTo("guswns1659");
    }
}

