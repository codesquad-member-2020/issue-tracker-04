package com.codesquad.issue04.web.dto.response.issue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class IssueDetailResponseDto implements ResponseDto {

	private Long id;
	private String title;
	private List<Comment> comments;
	private Set<Label> labels;
	private Milestone milestone;
	private RealUser realUser;
	private Status status;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	@Builder
	public IssueDetailResponseDto(Issue issue) {

		this.id = issue.getId();
		this.title = issue.getTitle();
		this.comments = issue.getComments().returnCommentsCreatingNewList();
		this.labels = issue.getLabels().getLabelsAfterCreatingNewSet();
		this.milestone = issue.getMilestone();
		this.realUser = issue.getUser();
		this.status = issue.getStatus();
	}

	public static IssueDetailResponseDto of(Issue issue) {
		return IssueDetailResponseDto.builder()
			.issue(issue)
			.build();
	}
}
