package com.codesquad.issue04.web.dto.request.issue;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IssueCreateRequestDto {

	private String title;
	private String commentContent;
	private String writerGitHubId;
	private List<String> photoUrl;

	public IssueCreateRequestDto(String title) {
		this.title = title;
		this.photoUrl = Collections.emptyList();
	}

	public IssueCreateRequestDto(String title, String commentContent) {
		this.title = title;
		this.commentContent = commentContent;
		this.photoUrl = Collections.emptyList();
	}

	public IssueCreateRequestDto(String title, String commentContent,
		List<String> photoUrl) {
		this.title = title;
		this.commentContent = commentContent;
		this.photoUrl = photoUrl;
	}
}
