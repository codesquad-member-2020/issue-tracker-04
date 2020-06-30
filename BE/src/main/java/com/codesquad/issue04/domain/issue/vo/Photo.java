package com.codesquad.issue04.domain.issue.vo;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Embeddable
public class Photo {
	private String url;

	private Photo(String url) {
		this.url = url;
	}

	public static Photo ofUrl(final String url) {
		return new Photo(url);
	}
}
