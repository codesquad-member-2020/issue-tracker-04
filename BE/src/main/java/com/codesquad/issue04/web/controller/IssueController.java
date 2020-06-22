package com.codesquad.issue04.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.service.IssueService;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/issues")
@AllArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping
	public ResponseEntity<ResponseDto> getIssues(@RequestParam(value = "status", defaultValue = "all") String status) {
		final String ALL_ISSUES = "all";
		if (status.equals(Status.OPEN.getName())) {
			return new ResponseEntity<>(issueService.getOpenIssueOverviews(), HttpStatus.OK);
		}
		if (status.equals(Status.CLOSED.getName())) {
			return new ResponseEntity<>(issueService.getClosedIssueOverviews(), HttpStatus.OK);
		}
		if (status.equals(ALL_ISSUES)) {
			return new ResponseEntity<>(issueService.getAllIssueOverviews(), HttpStatus.OK);
		}
		return new ResponseEntity<>(
			new ErrorResponseDto(HttpStatus.NOT_ACCEPTABLE.value(), new RuntimeException("wrong input but check")),
			HttpStatus.NOT_ACCEPTABLE);
	}
}
