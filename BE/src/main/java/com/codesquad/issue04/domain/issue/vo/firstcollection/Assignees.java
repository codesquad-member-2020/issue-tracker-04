package com.codesquad.issue04.domain.issue.vo.firstcollection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.codesquad.issue04.domain.user.RealUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Assignees {

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name = "assignee",
		joinColumns = @JoinColumn(name = "issue_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<RealUser> assignees = new ArrayList<>();

	private Assignees(List<RealUser> assignees) {
		this.assignees = assignees;
	}

	public boolean hasAssignees() {
		return this.getAssignees().size() > 0;
	}

	public RealUser findAssigneeByUserGitHubId(String userGitHubId) {
		return assignees.stream()
			.filter(assignee -> assignee.isMatchedGitHubId(userGitHubId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("not found"));
	}

	public List<RealUser> returnAssigneesCreatingNewList() {
		return new ArrayList<>(this.assignees);
	}

	public static Assignees ofAssignees(List<RealUser> assignees) {
		return new Assignees(assignees);
	}

	public RealUser attachNewAssignee(RealUser user) {
		this.assignees.add(user);
		return user;
	}

	public RealUser detachExistingAssignee(RealUser user) {
		this.assignees.remove(user);
		return user;
	}
}
