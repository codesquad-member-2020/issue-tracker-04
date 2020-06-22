package com.codesquad.issue04.web.dto.response.issue;

import java.util.Optional;
import java.util.Set;

import com.codesquad.issue04.domain.issue.Comments;
import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.Labels;
import com.codesquad.issue04.domain.issue.Status;
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
		Labels labels = Optional.ofNullable(issue.getLabels()).orElse(Labels.ofNullLabels());
		RealUser realUser = issue.getUser();

		this.id = issue.getId();
		this.title = issue.getTitle();
		this.overview = comments.getOverview().getContent();
		this.commentCounts = comments.getCommentsSize();
		this.mileStonesTitle = milestone.getTitle();
		this.labelTitles = labels.getLabelStringSet();
		this.githubId = realUser.getGithubId();
		this.status = issue.getStatus();
	}

	public static IssueOverviewDto of(Issue issue) {
		return IssueOverviewDto.builder()
			.issue(issue)
			.build();
	}
}
