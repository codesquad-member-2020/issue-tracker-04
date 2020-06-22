package com.codesquad.issue04.domain.issue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;

@Getter
@Embeddable
public class Comments {
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issue", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	protected Comments() {
	}

	private Comments(List<Comment> comments) {
		this.comments = comments;
	}

	public static Comments of() {
		return new Comments();
	}

	public static Comments ofComments(List<Comment> comments) {
		return new Comments(comments);
	}

	public List<Comment> returnCommentsCreatingNewList() {
		return new ArrayList<>(this.comments);
	}

	public Comment getOverview() {
		return this.comments.get(0);
	}

	public int getCommentsSize() {
		return this.comments.size();
	}

	public Comment getCommentByIndex(int commentIndex) {
		return this.comments.get(commentIndex);
	}
}
