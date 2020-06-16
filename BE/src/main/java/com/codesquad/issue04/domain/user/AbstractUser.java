package com.codesquad.issue04.domain.user;

import java.util.List;

import com.codesquad.issue04.domain.issue.Issue;
import lombok.Getter;

@Getter
public abstract class AbstractUser {
	protected Long id;
	protected String name;
	protected String githubId;
	protected String image;
	protected List<Issue> issues;
	protected Role role;

	public abstract boolean isNil();
}
