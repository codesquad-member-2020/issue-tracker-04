package com.codesquad.issue04.domain.milestone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.web.dto.request.MilestoneUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "issues")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Builder
public class Milestone implements AbstractMilestone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private LocalDate dueDate;
	private String description;

	@JsonIgnore
	@OneToMany(mappedBy = "milestone")
	private List<Issue> issues = new ArrayList<>();

	@Override
	public boolean isNil() {
		return false;
	}

	public Milestone updateMilestone(final MilestoneUpdateRequestDto dto) {
		this.title = dto.getTitle();
		this.dueDate = dto.getDueDate();
		this.description = dto.getDescription();
		return this;
	}
}
