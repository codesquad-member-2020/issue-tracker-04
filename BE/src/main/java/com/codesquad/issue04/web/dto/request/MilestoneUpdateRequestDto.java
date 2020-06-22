package com.codesquad.issue04.web.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class MilestoneUpdateRequestDto {
	private Long id;
	private String title;
	private LocalDate dueDate;
	private String description;

	public MilestoneUpdateRequestDto(Long id, String title, LocalDate dueDate, String description) {
		this.id = id;
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
	}
}
