package com.codesquad.issue04.domain.issue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.user.RealUser;

public interface IssueRepository extends JpaRepository<Issue, Long> {

	Optional<Issue> findTopByOrderByIdDesc();
    Optional<List<Issue>> findIssuesByStatus(Status status);
    Optional<List<Issue>> findIssuesByStatusAndUserGithubId(Status status, String userId);
    Optional<List<Issue>> findIssuesByStatusAndAssignees(Status status, RealUser user);
}
