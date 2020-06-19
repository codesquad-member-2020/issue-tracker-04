package com.codesquad.issue04.domain.issue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.milestone.NullMilestone;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
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
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issue", cascade = CascadeType.REMOVE)
	private List<Comment> comments;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "label_has_issue",
		joinColumns = @JoinColumn(name = "issue_id"),
		inverseJoinColumns = @JoinColumn(name = "label_id")
	)
	private Set<Label> labels;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "assignee",
		joinColumns = @JoinColumn(name = "issue_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<RealUser> assignees;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "milestone_id"))
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
	private RealUser user;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Builder
	public Issue(Long id, String title, List<Comment> comments,
		Set<Label> labels, Milestone milestone, RealUser user) {

		this.id = id;
		this.title = Optional.ofNullable(title).orElse("직박구리");
		this.comments = Optional.ofNullable(comments).orElse(Collections.emptyList());
		this.labels = Optional.ofNullable(labels).orElse(Collections.emptySet());
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
}
