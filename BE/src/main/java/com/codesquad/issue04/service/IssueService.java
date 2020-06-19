package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.issue.IssueRepository;
import com.codesquad.issue04.web.dto.response.issue.IssueDetailResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;

	protected Issue findIssueById(Long issueId) {
		return issueRepository.findById(issueId)
			.orElseThrow(() -> new IllegalArgumentException("issue not found id: " + issueId));
	}

	protected List<Issue> findAllIssues() {
		return issueRepository.findAll();
	}

	List<IssueOverviewDto> findAllIssuesOverview() {
		List<Issue> issues = findAllIssues();
		return issues.stream()
			.map(IssueOverviewDto::of)
			.collect(Collectors.toList());
	}

	List<IssueOverviewDto> findAllOpenIssuesOverview() {
		List<Issue> issues = findAllIssues();
		return issues.stream()
			.filter(Issue::isOpen)
			.map(IssueOverviewDto::of)
			.collect(Collectors.toList());
	}

	List<IssueOverviewDto> findAllClosedIssuesOverview() {
		List<Issue> issues = findAllIssues();
		return issues.stream()
			.filter(Issue::isClosed)
			.map(IssueOverviewDto::of)
			.collect(Collectors.toList());
	}

	public IssueDetailResponseDto findIssueDetailById(Long issueId) {
		return IssueDetailResponseDto.of(findIssueById(issueId));
	}

	public IssueOverviewResponseDtos getAllIssueOverviews() {
		return IssueOverviewResponseDtos.builder()
			.allData(findAllIssuesOverview())
			.build();
	}

	public IssueOverviewResponseDtos getOpenIssueOverviews() {
		return IssueOverviewResponseDtos.builder()
			.allData(findAllOpenIssuesOverview())
			.build();
	}

	public IssueOverviewResponseDtos getClosedIssueOverviews() {
		return IssueOverviewResponseDtos.builder()
			.allData(findAllClosedIssuesOverview())
			.build();
	}

    public IssueOverviewResponseDtos getIssueOverviews() {
        return IssueOverviewResponseDtos.builder()
            .allData(findAllIssuesOverview())
            .build();
    }

    public List<Issue> getAllAssignedIssues() {
        List<Issue> issues = issueRepository.findAll();

        return issues.stream()
            .filter(Issue::hasAssignees)
            .collect(Collectors.toList());
    }
}
