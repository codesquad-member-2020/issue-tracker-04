package com.codesquad.issue04.web.dto.request.issue;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueLabelDetachRequestDto {
	private Long issueId;
	private String labelTitle;

	@Builder
	public IssueLabelDetachRequestDto(Long issueId, String labelTitle) {
		this.issueId = issueId;
		this.labelTitle = labelTitle;
	}
}
