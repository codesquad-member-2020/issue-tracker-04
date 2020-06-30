package com.codesquad.issue04.web.dto.response.label;

import com.codesquad.issue04.domain.label.Label;
import lombok.Getter;

@Getter
public class LabelDetailResponseDto {

	private Long id;
	private String title;
	private String color;
	private String description;

	private LabelDetailResponseDto(Label label) {
		this.id = label.getId();
		this.title = label.getTitle();
		this.color = label.getColor();
		this.description = label.getDescription();
	}

	public static LabelDetailResponseDto of(Label label) {
		return new LabelDetailResponseDto(label);
	}
}
