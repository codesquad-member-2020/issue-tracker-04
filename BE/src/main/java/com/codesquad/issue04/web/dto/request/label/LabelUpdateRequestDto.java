package com.codesquad.issue04.web.dto.request.label;

import lombok.Getter;

@Getter
public class LabelUpdateRequestDto {
	private Long id;
	private String title;
	private String color;
	private String description;

	public LabelUpdateRequestDto(Long id, String title, String color, String description) {
		this.id = id;
		this.title = title;
		this.color = color;
		this.description = description;
	}
}
