package com.codesquad.issue04.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.IssueRepository;
import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.milestone.MilestoneRepository;
import com.codesquad.issue04.domain.milestone.NullMilestone;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.domain.user.UserRepository;
import com.codesquad.issue04.utils.Filter;
import com.codesquad.issue04.web.dto.request.FilterParamRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentCreateRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentUpdateRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueAssigneeRequestDto;
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
import com.codesquad.issue04.web.dto.response.user.AssigneeDto;
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

    public List<Issue> filtering(FilterParamRequestDto filterParamRequestDto) {

        Status status = filterParamRequestDto.getStatus();
        String role = filterParamRequestDto.getRole();
        String option = filterParamRequestDto.getOption();
        String value = filterParamRequestDto.getValue();
        String userId = "guswns1659";
        RealUser user = userRepository.findByGithubId(userId).orElse(NullUser.of());

        List<Issue> issuesByFiltering = issueRepository.findIssuesByStatus(status)
            .orElse(new ArrayList<>());
        // 로그인한 사용자의 정보를 가져오는 코드. 아직 인터셉터 적용 안해서 주석처리.
        // String userId = (String)request.getAttribute("userId");
        if (!role.equals(Filter.EMPTY.param())) {
            issuesByFiltering = roleFiltering(role, status, userId, user, issuesByFiltering);
        }
        if (!option.equals(Filter.EMPTY.param())) {
            issuesByFiltering = optionFiltering(option, value, issuesByFiltering);
        }

        return issuesByFiltering;
    }

    private List<Issue> optionFiltering(String option, String value, List<Issue> issuesByFiltering) {
        if (option.equals(Filter.AUTHOR.param())) {
            return issuesByFiltering.stream()
                .filter(issue -> issue.isSameAuthor(value))
                .collect(Collectors.toList());
        }
        if (option.equals(Filter.MILESTONE.param())) {
            return issuesByFiltering.stream()
                .filter(issue -> issue.isSameMilestone(value))
                .collect(Collectors.toList());
        }
        if (option.equals(Filter.LABEL.param())) {
            return issuesByFiltering.stream()
                .filter(issue -> issue.isSameLabelExists(value))
                .collect(Collectors.toList());
        }
        if (option.equals(Filter.ASSIGNEE.param())) {
            return issuesByFiltering.stream()
                .filter(issue -> issue.isUserIdContainInAssignees(value))
                .collect(Collectors.toList());
        }
        return issuesByFiltering;
    }

    private List<Issue> roleFiltering(String role, Status status, String userId, RealUser user,
        List<Issue> issuesByFiltering) {
        if (role.equals(Filter.AUTHORED.param())) {
            return issueRepository.findIssuesByStatusAndUserGithubId(status, userId)
                .orElse(new ArrayList<>());
        }
        if (role.equals(Filter.ASSIGNED.param())) {
            return issueRepository.findIssuesByStatusAndUser(status, user)
                .orElse(new ArrayList<>());
        }
        if (role.equals(Filter.COMMENTED.param())) {
            return findIssuesByStatusAndCommentsByUser(issuesByFiltering, userId);
        }
        return issuesByFiltering;
    }

    private List<Issue> findIssuesByStatusAndCommentsByUser(List<Issue> issuesByFiltering, String userId) {
        return issuesByFiltering.stream()
            .filter(issue -> issue.isUserIdHasComment(userId))
            .collect(Collectors.toList());
    }

    public IssueOverviewResponseDtos responseFiltering(FilterParamRequestDto filterParamRequestDto) {
        List<Issue> issuesByFiltering = filtering(filterParamRequestDto);
        List<IssueOverviewDto> allData = issuesByFiltering.stream()
            .map(IssueOverviewDto::of)
            .collect(Collectors.toList());

        return IssueOverviewResponseDtos.of(allData);
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
		savedIssue.addInitialComment(firstComment);
		return IssueDetailResponseDto.builder()
			.issue(newIssue)
			.build();
	}

	@Transactional
	public IssueDetailResponseDto findLatestIssue() {
		Issue latestIssue = issueRepository.findTopByOrderByIdDesc()
			.orElseThrow(() -> new IllegalArgumentException("not found."));
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
		issue.getComments().addComment(addedComment);
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

	@Transactional
	public Comment deleteComment(final CommentDeleteRequestDto dto) {
		Issue issue = findIssueById(dto.getIssueId());
		Comment comment = findCommentById(issue, dto);
		RealUser user = findUserByGithubId(dto);
		if (validateUserCommentPermission(comment, user)) {
			issue.deleteCommentById(dto.getCommentId());
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

	private RealUser findUserByGithubId(final IssueAssigneeRequestDto dto) {
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

	@Transactional
	public Milestone updateMilestone(final Long issueId, final Long milestoneId) {
		Issue issue = findIssueById(issueId);
		Milestone milestone = getMilestoneById(milestoneId);
		issue.updateMilestone(milestone);
		return milestone;
	}

	@Transactional
	public Milestone deleteMilestone(final Long issueId, final Long milestoneId) {
		Issue issue = findIssueById(issueId);
		return issue.deleteMilestone(milestoneId);
	}

	private Label getLabelById(final Long labelId) {
		return labelService.findLabelById(labelId);
	}

	private Label getLabelByTitle(final String labelTitle) {
		return labelService.findLabelByTitle(labelTitle);
	}

	@Transactional
	public Label attachLabel(final Long issueId, final Long labelId) {
		Issue issue = findIssueById(issueId);
		Label label = getLabelById(labelId);
		issue.addNewLabel(label);
		return label;
	}

	@Transactional
	public Label attachLabel(final Long issueId, final String labelTitle) {
		Issue issue = findIssueById(issueId);
		Label label = getLabelByTitle(labelTitle);
		issue.addNewLabel(label);
		return label;
	}

	@Transactional
	public Label detachLabel(final Long issueId, final Long labelId) {
		Issue issue = findIssueById(issueId);
		Label label = getLabelById(labelId);
		issue.deleteExistingLabel(label);
		return label;
	}

	@Transactional
	public Label detachLabel(final Long issueId, final String labelTitle) {
		Issue issue = findIssueById(issueId);
		Label label = getLabelByTitle(labelTitle);
		issue.deleteExistingLabel(label);
		return label;
	}

	@Transactional
	public AssigneeDto attachNewAssignee(IssueAssigneeRequestDto dto) {
		Issue issue = findIssueById(dto.getIssueId());
		RealUser user = findUserByGithubId(dto);
		issue.getAssignees().attachNewAssignee(user);
		return AssigneeDto.of(user);
	}

	@Transactional
	public AssigneeDto detachExistingAssignee(IssueAssigneeRequestDto dto) {
		Issue issue = findIssueById(dto.getIssueId());
		RealUser user = findUserByGithubId(dto);
		issue.detachExistingAssignee(user);
		return AssigneeDto.of(user);
	}
}
