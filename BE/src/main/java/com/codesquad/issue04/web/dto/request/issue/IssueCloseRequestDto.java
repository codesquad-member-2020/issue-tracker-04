package com.codesquad.issue04.web.dto.request.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCloseRequestDto {
	private Long id;

	public IssueCloseRequestDto(Long id) {
		this.id = id;
	}
}
