package com.codesquad.issue04.domain.label;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
	Label findTopByOrderByIdDesc();
}
