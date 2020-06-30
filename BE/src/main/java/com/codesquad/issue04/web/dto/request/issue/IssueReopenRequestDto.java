package com.codesquad.issue04.web.dto.request.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueReopenRequestDto {
	private Long id;

	public IssueReopenRequestDto(Long id) {
		this.id = id;
	}
}
