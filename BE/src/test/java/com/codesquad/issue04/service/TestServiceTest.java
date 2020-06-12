package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.web.dto.response.IssueDetailResponseDto;
import com.codesquad.issue04.web.dto.response.IssueOverviewDto;

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
	@CsvSource({"0, 1", "1, 1"})
	@ParameterizedTest
	void 댓글_별로_이모지가_불러진다(int commentIndex, int expectedSize) {
		assertThat(testService.findIssueById(1L).getComments().get(commentIndex).getEmojis().size()).isEqualTo(
			expectedSize);
	}

	@Transactional
	@DisplayName("이슈 전체를 가져온다.")
	@CsvSource({"0, SQL 작성", "1, 스키마 작성"})
	@ParameterizedTest
	void 이슈_전체를_가져온다(int index, String expectedTitle) {

		List<Issue> issues = testService.findAllIssues();
		assertThat(issues.get(index).getTitle()).isEqualTo(expectedTitle);
	}

	@Transactional
	@DisplayName("이슈 전체를 가져와 각 이슈의 요약을 보여준다.")
	@Test
	void 전체_이슈의_요약을_생성한다() {

		List<IssueOverviewDto> issueOverviewDtos = testService.findAllIssuesOverview();

		assertThat(issueOverviewDtos.get(0)).isInstanceOf(IssueOverviewDto.class);
	}

	@Transactional
	@DisplayName("각 이슈의 디테일을 보여준다.")
	@CsvSource({"1, SQL 작성", "2, 스키마 작성"})
	@ParameterizedTest
	void 각_이슈의_디테일을_보여준다(Long id, String title) {

		IssueDetailResponseDto issueDetailResponseDto = testService.findIssueDetailById(id);

		assertThat(issueDetailResponseDto.getId()).isEqualTo(id);
		assertThat(issueDetailResponseDto.getTitle()).isEqualTo(title);
		assertThat(issueDetailResponseDto.getUser().getIssues().size()).isGreaterThan(0);
	}

}
