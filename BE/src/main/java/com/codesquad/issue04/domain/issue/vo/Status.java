package com.codesquad.issue04.domain.issue.vo;

import lombok.Getter;

@Getter
public enum Status {
	OPEN("open", true),
	CLOSED("closed", false);

	private String name;
	private boolean open;

	Status(String name, boolean open) {
		this.name = name;
		this.open = open;
	}
}
