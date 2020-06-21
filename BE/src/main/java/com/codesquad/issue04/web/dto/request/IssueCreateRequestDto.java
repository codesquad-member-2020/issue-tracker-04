package com.codesquad.issue04.web.dto.request;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class IssueCreateRequestDto {

	private String title;
	private String commentContent;
	private String writerGitHubId;
	private List<String> photoUrl;

	public IssueCreateRequestDto(String title, String writerGitHubId) {
		this.title = title;
		this.writerGitHubId = writerGitHubId;
		this.photoUrl = Collections.emptyList();
	}

	public IssueCreateRequestDto(String title, String commentContent, String writerGitHubId) {
		this.title = title;
		this.commentContent = commentContent;
		this.writerGitHubId = writerGitHubId;
		this.photoUrl = Collections.emptyList();
	}

	public IssueCreateRequestDto(String title, String commentContent, String writerGitHubId,
		List<String> photoUrl) {
		this.title = title;
		this.commentContent = commentContent;
		this.writerGitHubId = writerGitHubId;
		this.photoUrl = photoUrl;
	}
}
