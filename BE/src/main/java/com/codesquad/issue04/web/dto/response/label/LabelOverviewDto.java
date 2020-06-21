package com.codesquad.issue04.web.dto.response.label;

import com.codesquad.issue04.domain.label.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class LabelOverviewDto {
	private Long id;
	private String title;
	private String color;

	public LabelOverviewDto(Label label) {
		this.id = label.getId();
		this.title = label.getTitle();
		this.color = label.getColor();
	}

	public static LabelOverviewDto of(Label label) {
		return new LabelOverviewDto(label);
	}
}
