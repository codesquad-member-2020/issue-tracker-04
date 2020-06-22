package com.codesquad.issue04.domain.issue;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Comments;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Labels;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.milestone.NullMilestone;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.utils.BaseTimeEntity;
import com.codesquad.issue04.web.dto.request.IssueUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = {"comments", "assignees"})
@Entity
public class Issue extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	private String title;

	@Embedded
	private Comments comments;

	@Embedded
	private Labels labels;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
		name = "assignee",
		joinColumns = @JoinColumn(name = "issue_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<RealUser> assignees;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "milestone_id"))
	private Milestone milestone;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
	private RealUser user;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Builder
	public Issue(Long id, String title, Comments comments,
		Labels labels, Milestone milestone, RealUser user) {

		this.id = id;
		this.title = Optional.ofNullable(title).orElse("직박구리");
		this.comments = Optional.ofNullable(comments).orElse(Comments.ofNullComments());
		this.labels = Optional.ofNullable(labels).orElse(Labels.ofNullLabels());
		this.milestone = Optional.ofNullable(milestone).orElse(NullMilestone.of());
		this.user = Optional.ofNullable(user).orElse(NullUser.of());
	}

	public boolean isOpen() {
		return this.status == Status.OPEN;
	}

	public boolean isClosed() {
		return this.status == Status.CLOSED;
	}

	public Status changeStatusToOpen() {
		if (this.status.isOpen()) {
			throw new IllegalArgumentException("this issue is already opened.");
		}
		Status openStatus = Status.OPEN;
		this.status = openStatus;
		return openStatus;
	}

	public Status changeStatusToClosed() {
		if (!this.status.isOpen()) {
			throw new IllegalArgumentException("this issue is already closed.");
		}
		Status closedStatus = Status.CLOSED;
		this.status = closedStatus;
		return closedStatus;
	}

	public boolean hasAssignees() {
		return this.getAssignees().size() > 0;
	}

	public Comment addComment(Comment comment) {
		List<Comment> newCommentList = this.comments.returnCommentsCreatingNewList();
		newCommentList.add(comment);
		this.comments = Comments.ofComments(newCommentList);
		return comment;
	}

	public Issue updateIssue(IssueUpdateRequestDto dto) {
		if (! dto.getTitle().equals(this.title)) {
			this.title = dto.getTitle();
		}
		return this;
	}

	public Comment getIssueOverview() {
		return this.comments.getOverview();
	}

	public Comment getCommentByIndex(int commentIndex) {
		return this.comments.getCommentByIndex(commentIndex);
	}

	public Comment getLatestComment() {
		return this.comments.getLatestComment();
	}

	public Comment findCommentById(Long commentId) {
		return this.comments.findCommentById(commentId);
	}

	public Comment deleteCommentById(Long commentId) {
		return this.comments.deleteCommentById(commentId);
	}
}
