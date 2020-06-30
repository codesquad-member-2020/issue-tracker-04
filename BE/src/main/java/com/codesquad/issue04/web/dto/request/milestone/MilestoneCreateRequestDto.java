package com.codesquad.issue04.web.dto.request.milestone;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class MilestoneCreateRequestDto {
	private String title;
	private LocalDate dueDate;
	private String description;

	public MilestoneCreateRequestDto(String title, LocalDate dueDate, String description) {
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
	}
}
