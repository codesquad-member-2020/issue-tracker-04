package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public abstract class CommentRequestDto {
	protected Long issueId;
	protected Long commentId;
	protected String userGitHubId;
}
