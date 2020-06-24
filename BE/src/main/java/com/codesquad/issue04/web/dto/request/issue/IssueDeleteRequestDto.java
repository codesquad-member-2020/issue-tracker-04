package com.codesquad.issue04.web.dto.request.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueDeleteRequestDto {
	private Long id;

	public IssueDeleteRequestDto(Long id) {
		this.id = id;
	}
}
