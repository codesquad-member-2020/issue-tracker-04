package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.web.dto.request.IssueCreateRequestDto;
import com.codesquad.issue04.web.dto.request.IssueDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.IssueUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueDetailResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewDto;

@SpringBootTest
public class IssueServiceTest {

	@Autowired
	private IssueService issueService;

	@Autowired
	private UserService userService;

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
		assertThat(issueDetailResponseDto.getRealUser().getOwnedIssues().size()).isGreaterThan(0);
	}

	@Transactional
	@DisplayName("이슈가 할당된 유저를 가져온다.")
	@Test
	void 이슈가_할당된_유저를_가져온다() {
		Long issueId = 1L;
		Issue issue = issueService.findIssueById(issueId);

		assertThat(issue.getAssignees().get(0)).isInstanceOf(RealUser.class);
		assertThat(issue.getAssignees().get(0).getGithubId()).isEqualTo("guswns1659");
	}

	@Transactional
	@DisplayName("새로운 이슈가 추가된다.")
	@CsvSource({"test title, test comment, test_id"})
	@ParameterizedTest
	void 새로운_이슈_하나가_추가된다(String title, String comment, String githubId) {
		List<String> photoUrls = Arrays.asList("codesquad.kr", "edu.nextstep.camp", "woowacourse.github.io");
		IssueCreateRequestDto dto = new IssueCreateRequestDto(title, comment, githubId, photoUrls);
		issueService.createNewIssue(dto);

		assertThat(issueService.findLatestIssue()).isInstanceOf(IssueDetailResponseDto.class);
		assertThat(issueService.findLatestIssue().getTitle()).isEqualTo(title);
	}

	@Transactional
	@DisplayName("기존의 이슈가 업데이트된다.")
	@CsvSource({"1, updated title, guswns1659, jypthemiracle"})
	@ParameterizedTest
	void 기존의_이슈의_제목이_업데이트된다(Long id, String title, String githubId, String notAuthorUserId) {
		IssueUpdateRequestDto dto = new IssueUpdateRequestDto(id, title);
		RealUser user = userService.getUserByGitHubId(githubId);
		IssueDetailResponseDto detailResponseDto = (IssueDetailResponseDto) issueService.updateExistingIssue(dto, user);

		assertAll(
			() -> assertThat(detailResponseDto.getTitle()).isEqualTo(title),
			() -> assertThat(issueService.findIssueById(1L).getTitle()).isEqualTo(title)
		);

		RealUser notAuthorUser = userService.getUserByGitHubId(notAuthorUserId);
		assertThat(issueService.updateExistingIssue(dto, notAuthorUser)).isInstanceOf(ErrorResponseDto.class);
	}

	@Transactional
	@DisplayName("기존의 이슈를 삭제한다. 이 때 사용자를 비교하여 자신의 이슈만 삭제할 수 있도록 한다.")
	@CsvSource({"1, guswns1659"})
	@ParameterizedTest
	void 기존의_이슈를_삭제한다(Long id, String githubId) {
		IssueDeleteRequestDto dto = new IssueDeleteRequestDto(id);
		RealUser user = userService.getUserByGitHubId(githubId);
		IssueDetailResponseDto detailResponseDto = (IssueDetailResponseDto) issueService.deleteExistingIssue(dto, user);
		assertThat(detailResponseDto.getId()).isEqualTo(id);
		assertThatThrownBy(
			() -> issueService.findIssueById(id)
		);
	}
}
