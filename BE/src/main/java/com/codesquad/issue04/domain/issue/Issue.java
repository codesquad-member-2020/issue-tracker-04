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
import javax.persistence.ManyToOne;

import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Assignees;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Comments;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Labels;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.milestone.NullMilestone;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.utils.BaseTimeEntity;
import com.codesquad.issue04.web.dto.request.comment.CommentUpdateRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
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

	@Embedded
	private Assignees assignees;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "milestone_id", nullable = true)
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
		if (! this.status.isOpen()) {
			throw new IllegalArgumentException("this issue is already closed.");
		}
		Status closedStatus = Status.CLOSED;
		this.status = closedStatus;
		return closedStatus;
	}

	public boolean hasAssignees() {
		return this.getAssignees().hasAssignees();
	}

	public Comment addInitialComment(final Comment comment) {
		List<Comment> newCommentList = this.comments.returnCommentsCreatingNewList();
		newCommentList.add(comment);
		this.comments = Comments.ofComments(newCommentList);
		return comment;
	}

	public Issue updateIssue(final IssueUpdateRequestDto dto) {
		if (! dto.getTitle().equals(this.title)) {
			this.title = dto.getTitle();
		}
		return this;
	}

	public Comment getIssueOverview() {
		return this.comments.getOverview();
	}

	public Comment getCommentByIndex(final int commentIndex) {
		return this.comments.getCommentByIndex(commentIndex);
	}

	public Comment getLatestComment() {
		return this.comments.getLatestComment();
	}

	public Comment findCommentById(final Long commentId) {
		return this.comments.findCommentById(commentId);
	}

	public Comment deleteCommentById(final Long commentId) {
		Comment comment = comments.findCommentById(commentId);
		this.comments.deleteCommentById(commentId);
		return comment;
	}

	public Comment modifyCommentByDto(final CommentUpdateRequestDto dto) {
		doesMatchId(dto);
		return this.comments.modifyCommentByDto(dto);
	}

	private void doesMatchId(final CommentUpdateRequestDto dto) {
		if (! this.user.isMatchedGitHubId(dto.getUserGitHubId())) {
			throw new IllegalArgumentException("not matched issue");
		}
	}

	public Milestone updateMilestone(final Milestone milestone) {
		this.milestone = milestone;
		return milestone;
	}

	public Milestone deleteMilestone(final Long milestoneId) {
		if (milestone.getId().equals(milestoneId)) {
			this.milestone = NullMilestone.of();
		}
		return this.milestone;
	}

	public boolean checkLabelsContainsByLabelId(final Long labelId) {
		return labels.checkLabelsContainsByLabelId(labelId);
	}

	public Label addNewLabel(final Label label) {
		return labels.addNewLabel(label);
	}

	public Label deleteExistingLabel(final Label label) {
		return labels.deleteExistingLabel(label);
	}

	public boolean isUserIdContainInAssignees(String userId) {
		return this.assignees.getAssignees().stream()
			.anyMatch(assignee -> assignee.isSameUser(userId));
	}

	public boolean isUserIdHasComment(String userId) {
		return this.comments.hasUserId(userId);
	}

	public boolean isSameLabelExists(String labelName) {
		return this.labels.isSameLabelExists(labelName);
	}

	public boolean isSameMilestone(String milestoneTitle) {
		return this.milestone.getTitle().equals(milestoneTitle);
	}

	public boolean isSameAuthor(String userId) {
		return this.user.getGithubId().equals(userId);
	}

	public RealUser detachExistingAssignee(final RealUser user) {
		this.assignees.detachExistingAssignee(user);
		return user;
	}
}
