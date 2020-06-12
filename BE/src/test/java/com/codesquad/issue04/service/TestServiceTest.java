package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;

@SpringBootTest
public class TestServiceTest {

	@Autowired
	private TestService testService;

	@Transactional
	@DisplayName("이슈 하나를 테스트로 가져온다.")
	@Test
	void 이슈_하나를_가져온다() {

		Issue issueById = testService.findIssueById(1L);
		assertThat(issueById).isInstanceOf(Issue.class);
		assertThat(issueById.getTitle()).isEqualTo("SQL 작성");
		assertThat(issueById.getComments().get(0).getContent()).isEqualTo("아하하 어렵네요.");
		assertThat(issueById.getComments().get(0).getPhotos().size()).isGreaterThan(0);
	}

	@Transactional
	@DisplayName("댓글 별로 이모지가 별도로 가져와진다. 0번째 댓글은 1개의 이모지를 지니고, 1번째 댓글은 2개의 이모지를 지닌다.")
	@CsvSource({"0, 1", "1, 2"})
	@ParameterizedTest
	void 댓글_별로_이모지가_불러진다(int commentIndex, int expectedSize) {
		assertThat(testService.findIssueById(1L).getComments().get(commentIndex).getEmojis().size()).isEqualTo(
			expectedSize);
	}
}
