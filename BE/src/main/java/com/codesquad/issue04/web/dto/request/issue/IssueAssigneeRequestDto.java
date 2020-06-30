package com.codesquad.issue04.web.dto.request.issue;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueAssigneeRequestDto {
	private Long issueId;
	private String userGitHubId;

	@Builder
	public IssueAssigneeRequestDto(Long issueId, String userGitHubId) {
		this.issueId = issueId;
		this.userGitHubId = userGitHubId;
	}
}
