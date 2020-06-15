package com.codesquad.issue04.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.service.TestService;
import com.codesquad.issue04.web.dto.response.IssueOverviewResponseDtos;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TestController {

	private final TestService testService;

	@GetMapping("api/v1/issues")
	public IssueOverviewResponseDtos getIssues() {
		return testService.getIssues();
	}
}
