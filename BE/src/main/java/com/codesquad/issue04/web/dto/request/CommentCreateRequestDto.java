package com.codesquad.issue04.web.dto.request;

import java.util.List;

import com.codesquad.issue04.domain.issue.vo.Emoji;
import com.codesquad.issue04.domain.issue.vo.Photo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
	private Long issueId;
	private Long userId;
	private String content;
	private List<Photo> photos;
	private List<Emoji> emojis;

	@Builder
	public CommentCreateRequestDto(Long issueId, Long userId, String content,
		List<Photo> photos, List<Emoji> emojis) {
		this.issueId = issueId;
		this.userId = userId;
		this.content = content;
		this.photos = photos;
		this.emojis = emojis;
	}
}
