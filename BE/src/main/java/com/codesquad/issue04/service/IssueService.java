package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.IssueRepository;
import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.milestone.MilestoneRepository;
import com.codesquad.issue04.domain.milestone.NullMilestone;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.domain.user.UserRepository;
import com.codesquad.issue04.web.dto.request.comment.CommentCreateRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentUpdateRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueCloseRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueCreateRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueReopenRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueDetailResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;
	private final UserRepository userRepository;
	private final MilestoneRepository milestoneRepository;
	private final LabelService labelService;

	protected Issue findIssueById(final Long issueId) {
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

	public IssueDetailResponseDto findIssueDetailById(final Long issueId) {
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

    public IssueOverviewResponseDtos getIssueOverviews() {
        return IssueOverviewResponseDtos.builder()
            .allData(findAllIssuesOverview())
            .build();
    }

    public List<Issue> getAllAssignedIssues() {
        List<Issue> issues = issueRepository.findAll();

        return issues.stream()
            .filter(Issue::hasAssignees)
            .collect(Collectors.toList());
    }

	@Transactional
	public IssueDetailResponseDto createNewIssue(final IssueCreateRequestDto dto) {
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
	public ResponseDto updateExistingIssue(final IssueUpdateRequestDto dto, final RealUser user) {
		Issue issue = findIssueById(dto.getId());
		if (! validateUserIssuePermission(issue, user)) {
			return createErrorResponseDto();
		}
		issue.updateIssue(dto);
		return IssueDetailResponseDto.of(issue);
	}

	@Transactional
	public ResponseDto deleteExistingIssue(final IssueDeleteRequestDto dto, final RealUser user) {
		Issue issue = findIssueById(dto.getId());
		if (! validateUserIssuePermission(issue, user)) {
			return createErrorResponseDto();
		}
		issueRepository.delete(issue);
		return IssueDetailResponseDto.of(issue);
	}

	@Transactional
	public IssueDetailResponseDto closeExistingIssue(final IssueCloseRequestDto dto) {
		Issue issue = findIssueById(dto.getId());
		issue.changeStatusToClosed();
		return IssueDetailResponseDto.of(issue);
	}

	@Transactional
	public IssueDetailResponseDto reopenExistingIssue(final IssueReopenRequestDto dto) {
		Issue issue = findIssueById(dto.getId());
		issue.changeStatusToOpen();
		return IssueDetailResponseDto.of(issue);
	}

	private boolean validateUserIssuePermission(final Issue issue, final RealUser user) {
		if (user.isNil()) {
			return false;
		}
		return issue.getUser().isMatchedGitHubId(user.getGithubId());
	}

	private ErrorResponseDto createErrorResponseDto() {
		return new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), new IllegalArgumentException("not allowed."));
	}

	@Transactional
	public Comment addNewComment(final CommentCreateRequestDto dto) {
		RealUser user = userRepository.findByGithubId(dto.getUserGitHubId()).orElseGet(NullUser::of);
		Issue issue = findIssueById(dto.getIssueId());
		Comment addedComment = Comment.ofDto(dto, user, issue);
		issue.addComment(addedComment);
		return addedComment;
	}

	@Transactional
	public Comment modifyComment(final CommentUpdateRequestDto dto) {
		Issue issue = findIssueById(dto.getIssueId());
		RealUser user = findUserByGithubId(dto);
		if (validateUserIssuePermission(issue, user)) {
			return issue.modifyCommentByDto(dto);
		}
		throw new IllegalArgumentException("not allowed to modify.");
	}

	public Comment deleteComment(final CommentDeleteRequestDto dto) {
		Issue issue = findIssueById(dto.getIssueId());
		Comment comment = findCommentById(issue, dto);
		RealUser user = findUserByGithubId(dto);
		if (validateUserCommentPermission(comment, user)) {
			issue.deleteCommentById(dto.getCommentId());
			issueRepository.save(issue);
			return comment;
		}
		throw new IllegalArgumentException("not allowed to delete.");
	}

	public Flux<Comment> findCommentsByIssueId(final Long issueId) {
		Issue issue = findIssueById(issueId);
		return Flux.fromStream(issue.getComments().getComments().stream());
	}

	private Comment findCommentById(final Issue issue, final CommentRequestDto dto) {
		return issue.findCommentById(dto.getCommentId());
	}

	private RealUser findUserByGithubId(final CommentRequestDto dto) {
		return userRepository.findByGithubId(dto.getUserGitHubId()).orElseGet(NullUser::of);
	}

	private boolean validateUserCommentPermission(final Comment comment, final RealUser user) {
		if (user.isNil()) {
			return false;
		}
		return comment.getUser().isMatchedGitHubId(user.getGithubId());
	}

	private Milestone getMilestoneById(final Long milestoneId) {
		return milestoneRepository.findById(milestoneId).orElseGet(NullMilestone::of);
	}

	public Milestone updateMilestone(final Long issueId, final Long milestoneId) {
		Issue issue = findIssueById(issueId);
		Milestone milestone = getMilestoneById(milestoneId);
		issue.updateMilestone(milestone);
		return milestone;
	}

	public Milestone deleteMilestone(final Long issueId, final Long milestoneId) {
		Issue issue = findIssueById(issueId);
		return issue.deleteMilestone(milestoneId);
	}

	private Label getLabelById(final Long labelId) {
		return labelService.findLabelById(labelId);
	}

	public Label addNewLabel(final Long issueId, final Long labelId) {
		Issue issue = findIssueById(issueId);
		Label label = getLabelById(labelId);
		issue.addNewLabel(label);
		return label;
	}

	public Label deleteLabel(final Long issueId, final Long labelId) {
		Issue issue = findIssueById(issueId);
		Label label = getLabelById(labelId);
		issue.deleteExistingLabel(label);
		return label;
	}
}
