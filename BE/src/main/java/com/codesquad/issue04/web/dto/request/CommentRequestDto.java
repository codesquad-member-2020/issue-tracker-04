package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public abstract class CommentRequestDto {
	private Long issueId;
	private Long commentId;
	private String userGithubId;
}
