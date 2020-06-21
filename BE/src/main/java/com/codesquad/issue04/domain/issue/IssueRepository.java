package com.codesquad.issue04.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

	Issue findTopByOrderByIdDesc();
}
