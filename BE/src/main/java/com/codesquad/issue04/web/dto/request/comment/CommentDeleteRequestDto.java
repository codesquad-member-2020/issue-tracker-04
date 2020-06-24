package com.codesquad.issue04.web.dto.request.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDeleteRequestDto extends CommentRequestDto {

	@Builder
	public CommentDeleteRequestDto(Long issueId, Long commentId, String userGitHubId) {
		super.issueId = issueId;
		super.commentId = commentId;
		super.userGitHubId = userGitHubId;
	}
}
