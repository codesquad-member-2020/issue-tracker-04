package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public class IssueUpdateRequestDto {

	private Long id;
	private String title;

	public IssueUpdateRequestDto(Long id, String title) {
		this.id = id;
		this.title = title;
	}
}
