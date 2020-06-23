package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public class CommentDeleteRequestDto extends CommentRequestDto {
	private Long issueId;
	private Long commentId;
	private String userGithubId;

	public CommentDeleteRequestDto(Long issueId, Long commentId, String userGithubId) {
		this.issueId = issueId;
		this.commentId = commentId;
		this.userGithubId = userGithubId;
	}
}
