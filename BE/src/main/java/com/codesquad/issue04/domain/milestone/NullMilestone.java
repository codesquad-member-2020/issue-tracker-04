package com.codesquad.issue04.domain.milestone;

import lombok.Getter;

@Getter
public class NullMilestone extends Milestone implements AbstractMilestone {

	private static String NULL_MILESTONE_MESSAGE = "빈 마일스톤입니다.";
	private String title;

	private NullMilestone() {
		this.title = NULL_MILESTONE_MESSAGE;
	}

	public static NullMilestone of() {
		return new NullMilestone();
	}

	@Override
	public boolean isNil() {
		return true;
	}
}
