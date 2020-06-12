package com.codesquad.issue04.domain.issue;

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

	public Photo(String url) {
		this.url = url;
	}
}
