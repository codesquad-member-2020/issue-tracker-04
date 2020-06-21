package com.codesquad.issue04.web.dto.response.issue;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.codesquad.issue04.domain.issue.Comments;
import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.Status;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.user.RealUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class IssueOverviewDto {

	private Long id;
	private String title;
	private String overview;
	private int commentCounts;
	private String mileStonesTitle;
	private Set<String> labelTitles;
	private String githubId;
	private Status status;

	@Builder
	public IssueOverviewDto(Issue issue) {

		Comments comments = Optional.ofNullable(issue.getComments()).orElse(Comments.of());
		Milestone milestone = issue.getMilestone();
		Set<Label> labels = issue.getLabels();
		RealUser realUser = issue.getUser();

		this.id = issue.getId();
		this.title = issue.getTitle();
		this.overview = comments.getOverview().getContent();
		this.commentCounts = comments.getCommentsSize();
		this.mileStonesTitle = milestone.getTitle();
		this.labelTitles = labels.stream()
			.map(Label::getTitle)
			.collect(Collectors.toSet());
		this.githubId = realUser.getGithubId();
		this.status = issue.getStatus();
	}

	public static IssueOverviewDto of(Issue issue) {
		return IssueOverviewDto.builder()
			.issue(issue)
			.build();
	}
}
