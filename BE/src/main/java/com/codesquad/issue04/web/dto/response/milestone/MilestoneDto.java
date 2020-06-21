package com.codesquad.issue04.web.dto.response.milestone;

import java.time.LocalDateTime;

import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MilestoneDto implements ResponseDto {
	private Long id;
	private String title;
	private LocalDateTime dueDate;
	private String description;

	public MilestoneDto(Milestone milestone) {
		this.id = milestone.getId();
		this.title = milestone.getTitle();
		this.dueDate = milestone.getDueDate();
		this.description = milestone.getDescription();
	}

	public static MilestoneDto of(Milestone milestone) {
		return new MilestoneDto(milestone);
	}
}
