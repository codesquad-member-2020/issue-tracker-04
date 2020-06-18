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
import com.codesquad.issue04.domain.issue.Status;
import com.codesquad.issue04.web.dto.response.issue.IssueDetailResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewDto;

@SpringBootTest
public class IssueServiceTest {

	@Autowired
	private IssueService issueService;

	@Transactional
	@DisplayName("이슈 하나를 테스트로 가져온다.")
	@Test
	void 이슈_하나를_가져온다() {

		Issue issueById = issueService.findIssueById(1L);
		assertThat(issueById).isInstanceOf(Issue.class);
		assertThat(issueById.getTitle()).isEqualTo("SQL 작성");
		assertThat(issueById.getComments().get(0).getContent()).isEqualTo("아하하 어렵네요.");
		assertThat(issueById.getComments().get(0).getPhotos().size()).isGreaterThan(0);
	}

	@Transactional
	@DisplayName("댓글 별로 이모지가 별도로 가져와진다. 1번째 댓글은 1개의 이모지를 지니고, 2번째 댓글은 2개의 이모지를 지닌다. 유의할 점은 commentId가 아니라 commentIndex라는 점이다.")
	@CsvSource({"1, 0, 1", "1, 1, 2"})
	@ParameterizedTest
	void 댓글_별로_이모지가_불러진다(Long issueId, int commentIndex, int expectedSize) {
		assertThat(issueService.findIssueById(issueId).getComments().get(commentIndex).getEmojis().size())
			.isEqualTo(expectedSize);
	}

	@Transactional
	@DisplayName("이슈 전체를 가져온다.")
	@CsvSource({"0, SQL 작성", "1, 스키마 작성"})
	@ParameterizedTest
	void 이슈_전체를_가져온다(int index, String expectedTitle) {

		List<Issue> issues = issueService.findAllIssues();
		assertThat(issues.get(index).getTitle()).isEqualTo(expectedTitle);
	}

	@Transactional
	@DisplayName("이슈 전체를 가져와 각 이슈의 요약을 보여준다.")
	@Test
	void 전체_이슈의_요약을_생성한다() {

		List<IssueOverviewDto> issueOverviewDtos = issueService.findAllIssuesOverview();

		assertThat(issueOverviewDtos.get(0)).isInstanceOf(IssueOverviewDto.class);
	}

	@Transactional
	@DisplayName("각 이슈의 디테일을 보여준다.")
	@CsvSource({"1, SQL 작성", "2, 스키마 작성"})
	@ParameterizedTest
	void 각_이슈의_디테일을_보여준다(Long id, String title) {

		IssueDetailResponseDto issueDetailResponseDto = issueService.findIssueDetailById(id);

		assertThat(issueDetailResponseDto.getId()).isEqualTo(id);
		assertThat(issueDetailResponseDto.getTitle()).isEqualTo(title);
		assertThat(issueDetailResponseDto.getRealUser().getIssues().size()).isGreaterThan(0);
	}

	@Transactional
	@DisplayName("이슈에 열린 이슈 또는 닫힌 이슈가 표시되며, 기본은 열린 이슈이다.")
	@Test
	void 이슈는_기본적으로_열려있다() {
		Issue issue = issueService.findIssueById(1L);
		assertThat(issue.getStatus().isOpen()).isEqualTo(true);
	}

	@Transactional
	@DisplayName("열린 이슈를 닫고 이를 검증한 후, 닫힌 이슈를 다시 열어서 이를 검증한다.")
	@Test
	void 열린_이슈를_닫고_닫힌_이슈를_열_수_있다() {
		//open to closed
		Issue issueBeforeClosed = issueService.findIssueById(1L);
		issueBeforeClosed.changeStatusToClosed();
		assertThat(issueService.findIssueById(1L).getStatus()).isEqualTo(Status.CLOSED);

		//closed to open
		Issue issueAfterClosed = issueService.findIssueById(1L);
		issueAfterClosed.changeStatusToOpen();
		assertThat(issueService.findIssueById(1L).getStatus()).isEqualTo(Status.OPEN);
	}

	@Transactional
	@DisplayName("열린 이슈만 필터링하여 가져올 수 있다.")
	@Test
	void 열린_이슈만_보여줄_수_있다() {
		List<IssueOverviewDto> OpenIssueOverviewDtoList = issueService.findAllOpenIssuesOverview();
		assertThat(
			OpenIssueOverviewDtoList.stream().map(IssueOverviewDto::getStatus)
		).isNotEqualTo(Status.CLOSED);
	}

	@Transactional
	@DisplayName("닫힌 이슈만 필터링하여 가져올 수 있다.")
	@Test
	void 닫힌_이슈만_보여줄_수_있다() {
		List<IssueOverviewDto> ClosedIssueOverviewDtoList = issueService.findAllClosedIssuesOverview();
		assertThat(
			ClosedIssueOverviewDtoList.stream().map(IssueOverviewDto::getStatus)
		).isNotEqualTo(Status.OPEN);
	}
}
