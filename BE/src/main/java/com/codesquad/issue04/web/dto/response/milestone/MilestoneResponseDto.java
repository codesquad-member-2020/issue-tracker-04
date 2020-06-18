package com.codesquad.issue04.web.dto.response.milestone;

import java.time.LocalDateTime;

import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class MilestoneResponseDto implements ResponseDto {
	private Long id;
	private String title;
	private LocalDateTime dueDate;
	private String description;

	public MilestoneResponseDto(Milestone milestone) {
		this.id = milestone.getId();
		this.title = milestone.getTitle();
		this.dueDate = milestone.getDueDate();
		this.description = milestone.getDescription();
	}

	public static MilestoneResponseDto of(Milestone milestone) {
		return new MilestoneResponseDto(milestone);
	}
}
