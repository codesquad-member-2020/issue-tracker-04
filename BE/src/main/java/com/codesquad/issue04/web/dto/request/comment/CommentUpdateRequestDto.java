package com.codesquad.issue04.web.dto.request.comment;

import java.util.List;

import com.codesquad.issue04.domain.issue.vo.Emoji;
import com.codesquad.issue04.domain.issue.vo.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto extends CommentRequestDto {
	private String content;
	private List<Photo> photos;
	private List<Emoji> emojis;

	@Builder
	public CommentUpdateRequestDto(Long issueId, Long commentId, String userGitHubId, String content,
		List<Photo> photos, List<Emoji> emojis) {
		super.issueId = issueId;
		super.commentId = commentId;
		super.userGitHubId = userGitHubId;
		this.content = content;
		this.photos = photos;
		this.emojis = emojis;
	}
}
