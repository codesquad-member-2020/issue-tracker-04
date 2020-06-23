package com.codesquad.issue04.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Comments;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.service.IssueService;
import com.codesquad.issue04.service.UserService;
import com.codesquad.issue04.web.dto.request.IssueCreateRequestDto;
import com.codesquad.issue04.web.dto.request.IssueDeleteRequestDtoTemp;
import com.codesquad.issue04.web.dto.request.IssueUpdateRequestDtoTemp;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/issue")
@AllArgsConstructor
public class IssueController {

	private final IssueService issueService;
	private final UserService userService;

	@GetMapping
	public Mono<ResponseEntity<ResponseDto>> getIssues(
		@RequestParam(value = "status", defaultValue = "all") String status) {
		final String ALL_ISSUES = "all";
		if (status.equals(Status.OPEN.getName())) {
			return Mono.just(new ResponseEntity<>(issueService.getOpenIssueOverviews(), HttpStatus.OK));
		}
		if (status.equals(Status.CLOSED.getName())) {
			return Mono.just(new ResponseEntity<>(issueService.getClosedIssueOverviews(), HttpStatus.OK));
		}
		if (status.equals(ALL_ISSUES)) {
			return Mono.just(new ResponseEntity<>(issueService.getAllIssueOverviews(), HttpStatus.OK));
		}
		return Mono.just(new ResponseEntity<>(
			new ErrorResponseDto(HttpStatus.NOT_ACCEPTABLE.value(), new RuntimeException("wrong input but check")),
			HttpStatus.NOT_ACCEPTABLE));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<ResponseDto>> getIssueDetailById(@PathVariable("id") Long issueId) {
		return Mono.just(new ResponseEntity<>(issueService.findIssueDetailById(issueId), HttpStatus.OK));
	}

	@PutMapping("/add")
	public Mono<ResponseEntity<ResponseDto>> createNewIssue(@RequestBody IssueCreateRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.createNewIssue(dto), HttpStatus.OK));
	}

	@PutMapping("/update")
	public Mono<ResponseEntity<ResponseDto>> updateExistingIssue(@RequestBody IssueUpdateRequestDtoTemp dto) {
		RealUser user = userService.getUserByGitHubId(dto.getUserGithubId());
		return Mono.just(new ResponseEntity<>(issueService.updateExistingIssue(dto, user), HttpStatus.OK));
	}

	@DeleteMapping("/delete")
	public Mono<ResponseEntity<ResponseDto>> deleteExistingIssue(@RequestBody IssueDeleteRequestDtoTemp dto) {
		RealUser user = userService.getUserByGitHubId(dto.getUserGitHubId());
		return Mono.just(new ResponseEntity<>(issueService.deleteExistingIssue(dto, user), HttpStatus.OK));
	}

	@GetMapping("/{id}/comment")
	public Mono<ResponseEntity<Comments>> findCommentsByIssueId(@PathVariable("id") Long issueId) {
		List<Comment> comments = issueService.findCommentsByIssueId(issueId).collectSortedList().block();
		Comments converted = Comments.ofComments(comments);
		return Mono.just(new ResponseEntity<>(converted, HttpStatus.OK));
	}
}
