package com.codesquad.issue04.web.dto.response;

import java.util.List;
import java.util.Set;

import com.codesquad.issue04.domain.issue.Comment;
import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class IssueDetailResponseDto {

	private Long id;
	private String title;
	private List<Comment> comments;
	private Set<Label> labels;
	private Milestone milestone;
	private User user;

	@Builder
	public IssueDetailResponseDto(Issue issue) {

		this.id = issue.getId();
		this.title = issue.getTitle();
		this.comments = issue.getComments();
		this.labels = issue.getLabels();
		this.milestone = issue.getMilestone();
		this.user = issue.getUser();
	}

	public static IssueDetailResponseDto of(Issue issue) {
		return IssueDetailResponseDto.builder()
			.issue(issue)
			.build();
	}
}
