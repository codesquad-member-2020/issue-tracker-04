package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codesquad.issue04.domain.issue.Issue;

@SpringBootTest
public class TestServiceTest {

	@Autowired
	private TestService testService;

	@DisplayName("이슈 하나를 테스트로 가져온다.")
	@Test
	void 이슈_하나를_가져온다() {
		assertThat(testService.findIssueById(1L)).isInstanceOf(Issue.class);
		assertThat(testService.findIssueById(1L).getTitle()).isEqualTo("SQL 작성");
		assertThat(testService.findIssueById(1L).getComments().get(0).getContent()).isEqualTo("아하하 어렵네요.");
	}
}
