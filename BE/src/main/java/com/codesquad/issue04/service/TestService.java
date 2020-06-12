package com.codesquad.issue04.service;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.IssueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
	private final IssueRepository issueRepository;
	protected Issue findIssueById(Long id) {
		return issueRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("issue not found id: " + id));
	}
}
