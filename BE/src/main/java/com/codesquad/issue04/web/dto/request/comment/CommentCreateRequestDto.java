package com.codesquad.issue04.web.dto.request.comment;

import java.util.List;

import com.codesquad.issue04.domain.issue.vo.Emoji;
import com.codesquad.issue04.domain.issue.vo.Photo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto extends CommentRequestDto {
	private String content;
	private List<Photo> photos;
	private List<Emoji> emojis;

	@Builder
	public CommentCreateRequestDto(Long issueId, String userGitHubId, String content,
		List<Photo> photos, List<Emoji> emojis) {
		super.issueId = issueId;
		super.userGitHubId = userGitHubId;
		this.content = content;
		this.photos = photos;
		this.emojis = emojis;
	}
}
