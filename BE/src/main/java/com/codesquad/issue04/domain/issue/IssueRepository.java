package com.codesquad.issue04.domain.issue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.user.RealUser;

public interface IssueRepository extends JpaRepository<Issue, Long> {

	Issue findTopByOrderByIdDesc();
    List<Issue> findIssuesByStatus(Status status);
    List<Issue> findIssuesByStatusAndUserGithubId(Status status, String userId);
    List<Issue> findIssuesByStatusAndAssignees(Status status, RealUser user);
}
