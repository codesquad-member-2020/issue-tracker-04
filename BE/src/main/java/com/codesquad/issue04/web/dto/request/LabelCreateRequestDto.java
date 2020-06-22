package com.codesquad.issue04.web.dto.request;

import lombok.Getter;

@Getter
public class LabelCreateRequestDto {
	private String title;
	private String color;
	private String description;

	public LabelCreateRequestDto(String title, String color, String description) {
		this.title = title;
		this.color = color;
		this.description = description;
	}
}
