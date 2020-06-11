package com.codesquad.issue04.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Milestone {

	private Long id;
	private String title;
	private LocalDateTime dueDate;
	private String description;

	@OneToMany(mappedBy = "milestone")
	private List<Issue> issues;
}
