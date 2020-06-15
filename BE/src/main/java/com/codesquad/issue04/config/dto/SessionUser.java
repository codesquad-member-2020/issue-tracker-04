package com.codesquad.issue04.config.dto;

import java.io.Serializable;

import com.codesquad.issue04.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

	private String name;
	private String githubId;
	private String image;

	public SessionUser(User user) {
		this.name = user.getName();
		this.githubId = user.getGithubId();
		this.image = user.getImage();
	}
}
