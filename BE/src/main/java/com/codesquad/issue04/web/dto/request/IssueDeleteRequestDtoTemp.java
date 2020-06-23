package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public class IssueDeleteRequestDtoTemp extends IssueDeleteRequestDto {
	private String userGitHubId;

	public IssueDeleteRequestDtoTemp(Long id, String userGitHubId) {
		super(id);
		this.userGitHubId = userGitHubId;
	}
}
