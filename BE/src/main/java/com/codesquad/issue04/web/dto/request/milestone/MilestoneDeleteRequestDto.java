package com.codesquad.issue04.web.dto.request.milestone;

import lombok.Getter;

@Getter
public class MilestoneDeleteRequestDto {
	private Long id;

	public MilestoneDeleteRequestDto(Long id) {
		this.id = id;
	}
}
