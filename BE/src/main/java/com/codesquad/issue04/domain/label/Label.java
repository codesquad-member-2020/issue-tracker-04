package com.codesquad.issue04.domain.label;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.web.dto.request.label.LabelUpdateRequestDto;
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
@Builder
@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String color;
	private String description;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "label_has_issue",
		joinColumns = @JoinColumn(name = "label_id"),
		inverseJoinColumns = @JoinColumn(name = "issue_id")
	)
	private Set<Issue> issues;

	public Label updateLabel(final LabelUpdateRequestDto dto) {
		this.title = dto.getTitle();
		this.color = dto.getColor();
		this.description = dto.getDescription();
		return this;
	}

	public boolean isSameLabel(String labelName) {
		return this.getTitle().equals(labelName);
	}
}
