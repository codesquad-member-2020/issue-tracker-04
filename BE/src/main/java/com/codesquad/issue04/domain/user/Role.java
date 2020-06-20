package com.codesquad.issue04.domain.user;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	GUEST("ROLE_GUEST", "손님"),
	USER("ROLE_USER", "일반 사용자");

	@Column(name = "role_key")
	private final String key;
	private final String title;
}
