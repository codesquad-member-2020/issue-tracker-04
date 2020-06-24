package com.codesquad.issue04.web.controller;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Status;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Comments;
import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.service.IssueService;
import com.codesquad.issue04.web.dto.request.FilterParamRequestDto;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;
import com.codesquad.issue04.service.UserService;
import com.codesquad.issue04.web.dto.request.comment.CommentCreateRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.comment.CommentUpdateRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueAssigneeRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueCloseRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueCreateRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueDeleteRequestDtoTemp;
import com.codesquad.issue04.web.dto.request.issue.IssueLabelAttachRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueLabelDetachRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueReopenRequestDto;
import com.codesquad.issue04.web.dto.request.issue.IssueUpdateRequestDtoTemp;
import com.codesquad.issue04.web.dto.response.ResponseDto;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.user.AssigneeDto;
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

	@PostMapping("/close")
	public Mono<ResponseEntity<ResponseDto>> closeExistingIssue(@RequestBody IssueCloseRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.closeExistingIssue(dto), HttpStatus.OK));
	}

	@PostMapping("/reopen")
	public Mono<ResponseEntity<ResponseDto>> reopenExistingIssue(@RequestBody IssueReopenRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.reopenExistingIssue(dto), HttpStatus.OK));
	}

	@GetMapping("/{id}/comment")
	public Mono<ResponseEntity<Comments>> findCommentsByIssueId(@PathVariable("id") Long issueId) {
		List<Comment> comments = issueService.findCommentsByIssueId(issueId).collectSortedList().block();
		Comments converted = Comments.ofComments(comments);
		return Mono.just(new ResponseEntity<>(converted, HttpStatus.OK));
	}

	@PutMapping("/comment/add")
	public Mono<ResponseEntity<Comment>> addNewComment(@RequestBody CommentCreateRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.addNewComment(dto), HttpStatus.OK));
	}

	@PutMapping("/comment/update")
	public Mono<ResponseEntity<Comment>> updateExistingComment(@RequestBody CommentUpdateRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.modifyComment(dto), HttpStatus.OK));
	}

	@DeleteMapping("/comment/delete")
	public Mono<ResponseEntity<Comment>> deleteExistingComment(@RequestBody CommentDeleteRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.deleteComment(dto), HttpStatus.OK));
	}

	@PutMapping("/label/attach")
	public Mono<ResponseEntity<Label>> attachNewLabelToExistingIssue(@RequestBody IssueLabelAttachRequestDto dto) {
		return Mono.just(
			new ResponseEntity<>(issueService.attachLabel(dto.getIssueId(), dto.getLabelTitle()), HttpStatus.OK));
	}

	@PutMapping("/label/detach")
	public Mono<ResponseEntity<Label>> detachExistingLabelFromExistingIssue(
		@RequestBody IssueLabelDetachRequestDto dto) {
		return Mono.just(
			new ResponseEntity<>(issueService.detachLabel(dto.getIssueId(), dto.getLabelTitle()), HttpStatus.OK));
	}

	@PutMapping("/assignee/attach")
	public Mono<ResponseEntity<AssigneeDto>> attachNewAssigneesToExistingIssue(@RequestBody
		IssueAssigneeRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.attachNewAssignee(dto), HttpStatus.OK));
	}

	@PutMapping("/assignee/detach")
	public Mono<ResponseEntity<AssigneeDto>> detachNewAssigneesToExistingIssue(@RequestBody
		IssueAssigneeRequestDto dto) {
		return Mono.just(new ResponseEntity<>(issueService.detachExistingAssignee(dto), HttpStatus.OK));
	}

	@GetMapping("/filter")
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
