package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.IssueRepository;
import com.codesquad.issue04.web.dto.response.IssueOverviewResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final IssueRepository issueRepository;

	protected Issue findIssueById(Long id) {
		return issueRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("issue not found id: " + id));
	}

	protected List<Issue> findAllIssues() {
		return issueRepository.findAll();
	}

	public List<IssueOverviewResponseDto> findAllIssuesOverview() {

		List<Issue> issues = findAllIssues();

		return issues.stream()
			.map(IssueOverviewResponseDto::of)
			.collect(Collectors.toList());
	}
}
