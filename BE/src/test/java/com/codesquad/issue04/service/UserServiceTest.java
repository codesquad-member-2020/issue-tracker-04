package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;
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
    @DisplayName("할당된 이슈 전체를 가져온다.")
    @Test
    void 할당된_전체이슈를_가져온다() {
        List<Issue> issues = userService.getAllAssignedIssues();
        assertThat(issues.get(0)).isInstanceOf(Issue.class);
        assertThat(issues.get(0).getUser().getGithubId()).isEqualTo("guswns1659");
    }
}

