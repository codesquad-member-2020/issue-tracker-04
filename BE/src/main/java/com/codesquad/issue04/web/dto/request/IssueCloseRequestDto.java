package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public class IssueCloseRequestDto {
	private Long id;

	public IssueCloseRequestDto(Long id) {
		this.id = id;
	}
}
