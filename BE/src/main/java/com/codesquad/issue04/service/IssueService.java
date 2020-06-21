package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Comment;
import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.IssueRepository;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.domain.user.UserRepository;
import com.codesquad.issue04.web.dto.request.IssueCreateRequestDto;
import com.codesquad.issue04.web.dto.request.IssueDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.IssueUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueDetailResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;
	private final UserRepository userRepository;

	protected Issue findIssueById(Long issueId) {
		return issueRepository.findById(issueId)
			.orElseThrow(() -> new IllegalArgumentException("issue not found id: " + issueId));
	}

	protected List<Issue> findAllIssues() {
		return issueRepository.findAll();
	}

	List<IssueOverviewDto> findAllIssuesOverview() {
		List<Issue> issues = findAllIssues();
		return issues.stream()
			.map(IssueOverviewDto::of)
			.collect(Collectors.toList());
	}

	List<IssueOverviewDto> findAllOpenIssuesOverview() {
		List<Issue> issues = findAllIssues();
		return issues.stream()
			.filter(Issue::isOpen)
			.map(IssueOverviewDto::of)
			.collect(Collectors.toList());
	}

	List<IssueOverviewDto> findAllClosedIssuesOverview() {
		List<Issue> issues = findAllIssues();
		return issues.stream()
			.filter(Issue::isClosed)
			.map(IssueOverviewDto::of)
			.collect(Collectors.toList());
	}

	public IssueDetailResponseDto findIssueDetailById(Long issueId) {
		return IssueDetailResponseDto.of(findIssueById(issueId));
	}

	public IssueOverviewResponseDtos getAllIssueOverviews() {
		return IssueOverviewResponseDtos.builder()
			.allData(findAllIssuesOverview())
			.build();
	}

	public IssueOverviewResponseDtos getOpenIssueOverviews() {
		return IssueOverviewResponseDtos.builder()
			.allData(findAllOpenIssuesOverview())
			.build();
	}

	public IssueOverviewResponseDtos getClosedIssueOverviews() {
		return IssueOverviewResponseDtos.builder()
			.allData(findAllClosedIssuesOverview())
			.build();
	}

	@Transactional
	public IssueDetailResponseDto createNewIssue(IssueCreateRequestDto dto) {
		Issue newIssue = Issue.builder()
			.title(dto.getTitle())
			.build();
		Issue savedIssue = issueRepository.save(newIssue);
		Comment firstComment = Comment.builder()
			.issue(savedIssue)
			.user(userRepository.findById(1L).orElse(NullUser.of()))
			.content(dto.getCommentContent())
			.build();
		savedIssue.addComment(firstComment);
		return IssueDetailResponseDto.builder()
			.issue(newIssue)
			.build();
	}

	@Transactional
	public IssueDetailResponseDto findLatestIssue() {
		Issue latestIssue = issueRepository.findTopByOrderByIdDesc();
		return IssueDetailResponseDto.builder()
			.issue(latestIssue)
			.build();
	}

	@Transactional
	public ResponseDto updateExistingIssue(IssueUpdateRequestDto dto, RealUser user) {
		Issue issue = findIssueById(dto.getId());
		if (! validateUserPermission(issue, user)) {
			return createErrorResponseDto();
		}
		issue.updateIssue(dto);
		return IssueDetailResponseDto.of(issue);
	}

	@Transactional
	public ResponseDto deleteExistingIssue(IssueDeleteRequestDto dto, RealUser user) {
		Issue issue = findIssueById(dto.getId());
		if (! validateUserPermission(issue, user)) {
			return createErrorResponseDto();
		}
		issueRepository.delete(issue);
		return IssueDetailResponseDto.of(issue);
	}

	private boolean validateUserPermission(Issue issue, RealUser user) {
		return issue.getUser().equals(user);
	}

	private ErrorResponseDto createErrorResponseDto() {
		return new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), new IllegalArgumentException("not allowed."));
	}
}
