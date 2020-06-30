package com.codesquad.issue04.web.dto.request.label;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
