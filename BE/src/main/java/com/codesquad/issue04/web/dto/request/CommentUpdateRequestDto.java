package com.codesquad.issue04.web.dto.request;

import java.util.List;

import com.codesquad.issue04.domain.issue.vo.Emoji;
import com.codesquad.issue04.domain.issue.vo.Photo;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
	private Long issueId;
	private Long commentId;
	private String content;
	private List<Photo> mockPhotos;
	private List<Emoji> mockEmojis;

	public CommentUpdateRequestDto(Long issueId, Long commentId, String content,
		List<Photo> mockPhotos, List<Emoji> mockEmojis) {
		this.issueId = issueId;
		this.commentId = commentId;
		this.content = content;
		this.mockPhotos = mockPhotos;
		this.mockEmojis = mockEmojis;
	}
}
