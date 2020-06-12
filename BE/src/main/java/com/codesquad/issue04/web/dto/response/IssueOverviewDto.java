package com.codesquad.issue04.web.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
public class IssueOverviewDto {

	private Long id;
	private String title;
	private String overview;
	private int commentCounts;
	private String mileStonesTitle;
	private Set<String> labelTitles;
	private String githubId;

	@Builder
	public IssueOverviewDto(Issue issue) {

		List<Comment> comments = Optional.ofNullable(issue.getComments()).orElse(new ArrayList<>());
		Milestone milestone = issue.getMilestone();
		Set<Label> labels = issue.getLabels();
		User user = issue.getUser();

		this.id = issue.getId();
		this.title = issue.getTitle();
		this.overview = comments.get(0).getContent();
		this.commentCounts = comments.size();
		this.mileStonesTitle = milestone.getTitle();
		this.labelTitles = labels.stream()
			.map(Label::getTitle)
			.collect(Collectors.toSet());
		this.githubId = user.getGithubId();
	}

	public static IssueOverviewDto of(Issue issue) {
		return IssueOverviewDto.builder()
			.issue(issue)
			.build();
	}
}
