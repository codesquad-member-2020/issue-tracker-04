package com.codesquad.issue04.domain.issue.vo.firstcollection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.codesquad.issue04.domain.issue.vo.Comment;
import lombok.Getter;

@Getter
@Embeddable
public class Comments {
	@OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	protected Comments() {
	}

	private Comments(List<Comment> comments) {
		this.comments = comments;
	}

	public static Comments ofNullComments() {
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

	public Comment getLatestComment() {
		int latestIndex = this.comments.size() - 1;
		return this.comments.get(latestIndex);
	}

	public Comment findCommentById(Long commentId) {
		return comments.stream()
			.filter(comment -> comment.getId().equals(commentId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("not found"));
	}

	public Comment deleteCommentById(Long commentId) {
		Comment deletedComment = findCommentById(commentId);
		this.comments.remove(deletedComment);
		return deletedComment;
	}
}
