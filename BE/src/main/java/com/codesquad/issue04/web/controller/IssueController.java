package com.codesquad.issue04.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.service.IssueService;
import com.codesquad.issue04.web.dto.request.FilterParamRequestDto;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/v1/issues")
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

	@GetMapping("v1/filter")
	public IssueOverviewResponseDtos filtering(@RequestParam String status, @RequestParam(required = false, defaultValue = "empty") String role,
																@RequestParam(required = false, defaultValue = "empty") String option,
																@RequestParam(required = false, defaultValue = "empty") String value,
		HttpServletRequest request) {

		FilterParamRequestDto filterParamRequestDto = FilterParamRequestDto.builder()
			.status(Status.valueOf(status.toUpperCase()))
			.role(role)
			.option(option)
			.value(value)
			.build();

		return issueService.responseFiltering(filterParamRequestDto);
	}
}
