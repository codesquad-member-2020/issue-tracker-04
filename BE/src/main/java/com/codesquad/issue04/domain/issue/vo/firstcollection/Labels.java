package com.codesquad.issue04.domain.issue.vo.firstcollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.codesquad.issue04.domain.label.Label;
import lombok.Getter;

@Getter
@Embeddable
public class Labels {

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(
		name = "label_has_issue",
		joinColumns = @JoinColumn(name = "issue_id"),
		inverseJoinColumns = @JoinColumn(name = "label_id")
	)
	private Set<Label> labels = new HashSet<>();

	protected Labels() {
	}

	public static Labels ofNullLabels() {
		return new Labels();
	}

	public Set<Label> getLabelsAfterCreatingNewSet() {
		return Collections.unmodifiableSet(this.labels);
	}

	public Set<String> getLabelStringSet() {
		return labels.stream()
			.map(Label::getTitle)
			.collect(Collectors.toSet());
	}

	public boolean checkLabelsContainsByLabelId(final Long labelId) {
		return labels.stream()
			.anyMatch(label -> label.getId().equals(labelId));
	}

	public Label addNewLabel(final Label label) {
		Set<Label> labelSet = new HashSet<>(this.labels);
		labelSet.add(label);
		this.labels = labelSet;
		return label;
	}

	public Label deleteExistingLabel(final Label label) {
		Set<Label> labelSet = new HashSet<>(this.labels);
		labelSet.remove(label);
		this.labels = labelSet;
		return label;
	}
}
