package com.codesquad.issue04.web.dto.request;

import java.util.List;

import com.codesquad.issue04.domain.issue.vo.Emoji;
import com.codesquad.issue04.domain.issue.vo.Photo;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto extends CommentRequestDto {
	private Long issueId;
	private Long commentId;
	private String userGithubId;
	private String content;
	private List<Photo> mockPhotos;
	private List<Emoji> mockEmojis;

	public CommentUpdateRequestDto(Long issueId, Long commentId, String userGithubId, String content,
		List<Photo> mockPhotos, List<Emoji> mockEmojis) {
		this.issueId = issueId;
		this.commentId = commentId;
		this.userGithubId = userGithubId;
		this.content = content;
		this.mockPhotos = mockPhotos;
		this.mockEmojis = mockEmojis;
	}
}
