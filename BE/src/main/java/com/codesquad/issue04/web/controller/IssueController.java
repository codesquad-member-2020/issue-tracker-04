package com.codesquad.issue04.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.service.IssueService;
import com.codesquad.issue04.web.dto.response.IssueOverviewResponseDtos;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("api/v1/issues")
	public IssueOverviewResponseDtos getIssues() {
		return issueService.getIssues();
	}
}
