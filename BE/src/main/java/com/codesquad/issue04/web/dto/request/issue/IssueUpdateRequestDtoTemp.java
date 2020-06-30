package com.codesquad.issue04.web.dto.request.issue;

import lombok.Getter;

@Getter
public class IssueUpdateRequestDtoTemp extends IssueUpdateRequestDto {

	private String userGithubId;

	public IssueUpdateRequestDtoTemp(Long id, String title, String userGithubId) {
		super(id, title);
		this.userGithubId = userGithubId;
	}
}
