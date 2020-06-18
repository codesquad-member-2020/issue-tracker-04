package com.codesquad.issue04.domain.user;

import java.util.Collections;

import lombok.Getter;

@Getter
public class NullUser extends RealUser implements AbstractUser {

	private static String NULL_USER_MESSAGE = "존재하지 않는 사용자입니다.";
	private String name;

	private NullUser() {
		this.name = "존재하지 않는 사용자입니다.";
		super.assignedIssues = Collections.emptyList();
	}

	public static NullUser of() {
		return new NullUser();
	}

	@Override
	public boolean isNil() {
		return true;
	}
}
