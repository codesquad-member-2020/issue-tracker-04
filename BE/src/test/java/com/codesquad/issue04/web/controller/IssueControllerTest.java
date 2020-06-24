package com.codesquad.issue04.web.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.vo.Comment;
import com.codesquad.issue04.domain.issue.vo.Emoji;
import com.codesquad.issue04.domain.issue.vo.Photo;
import com.codesquad.issue04.domain.issue.vo.firstcollection.Comments;
import com.codesquad.issue04.service.IssueService;
import com.codesquad.issue04.web.dto.request.CommentCreateRequestDto;
import com.codesquad.issue04.web.dto.request.CommentDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.CommentUpdateRequestDto;
import com.codesquad.issue04.web.dto.request.IssueCreateRequestDto;
import com.codesquad.issue04.web.dto.request.IssueDeleteRequestDtoTemp;
import com.codesquad.issue04.web.dto.request.IssueUpdateRequestDtoTemp;
import com.codesquad.issue04.web.dto.response.error.ErrorResponseDto;
import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "15000")
public class IssueControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private IssueService issueService;

	private String cookie;

	@Test
	void 전체_요약_이슈를_요청해서_응답한다() {

		// given
		String url = "http://localhost:" + port + "/api/issue";

		// when
		ResponseEntity<IssueOverviewResponseDtos> responseEntity
			= restTemplate.getForEntity(url, IssueOverviewResponseDtos.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isInstanceOf(IssueOverviewResponseDtos.class);
	}

	@Test
	void 전체_열린_이슈만_선별해서_응답한다() {
		String url = "http://localhost:" + port + "/api/issue?status=open";
		webTestClient.get()
			.uri(url)
			.exchange()
			.expectStatus().isEqualTo(200)
			.expectBodyList(IssueOverviewResponseDtos.class)
			.consumeWith(response ->
				assertThat(
					Objects.requireNonNull(response.getResponseBody()).stream()
						.allMatch(allData -> allData.getAllData().stream()
							.allMatch(issue -> issue.getStatus().isOpen()))
				).isEqualTo(true));
	}

	@Test
	void 전체_닫힌_이슈만_선별해서_응답한다() {
		String url = "http://localhost:" + port + "/api/issue?status=closed";
		webTestClient.get()
			.uri(url)
			.exchange()
			.expectStatus().isEqualTo(200)
			.expectBodyList(IssueOverviewResponseDtos.class)
			.consumeWith(response ->
				assertThat(
					Objects.requireNonNull(response.getResponseBody()).stream()
						.allMatch(allData -> allData.getAllData().stream()
							.noneMatch(issue -> issue.getStatus().isOpen()))
				).isEqualTo(true));
	}

	@Test
	void 잘못된_인자가_들어오는_경우_오류를_반환한다() {
		String url = "http://localhost:" + port + "/api/issue?status=wrong";
		ErrorResponseDto responseBody = webTestClient.get()
			.uri(url)
			.exchange()
			.expectStatus()
			.is4xxClientError()
			.expectBody(ErrorResponseDto.class)
			.returnResult()
			.getResponseBody();
		assert responseBody != null;
		assertThat(responseBody.getMessage()).isEqualTo("wrong input but check");
	}

	@Transactional
	@Test
	void 이슈_하나가_새로_생성된다() {
		String url = "http://localhost:" + port + "/api/issue/add";
		IssueCreateRequestDto request = new IssueCreateRequestDto(
			"Thanks Brian", "He reviewed in wonderful way for 6 months", "jypthemiracle",
			Arrays.asList("naver.com", "sigrid.com"));
		webTestClient.put()
			.uri(url)
			.header("Cookie", cookie)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(request), IssueCreateRequestDto.class)
			.exchange()
			.expectStatus()
			.isOk();
	}

	@Transactional
	@Test
	void 이슈_하나가_수정된다() {
		String url = "http://localhost:" + port + "/api/issue/update";
		IssueUpdateRequestDtoTemp request = new IssueUpdateRequestDtoTemp(
			1L, "We love wheejuni", "guswns1659");
		webTestClient.put()
			.uri(url)
			.header("Cookie", cookie)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(request), IssueUpdateRequestDtoTemp.class)
			.exchange()
			.expectStatus()
			.isOk();
	}

	@Transactional
	@Test
	void 이슈_하나가_삭제된다() {
		String url = "http://localhost:" + port + "/api/issue/delete";
		IssueDeleteRequestDtoTemp request = new IssueDeleteRequestDtoTemp(
			2L, "guswns1659");

		//then
		webTestClient
			.method(HttpMethod.DELETE)
			.uri(url)
			.header("Cookie", cookie)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(request), IssueDeleteRequestDtoTemp.class)
			.exchange()
			.expectStatus()
			.isOk();
	}

	@Test
	void 이슈_전체_댓글을_가져온다() {
		Long issueId = 1L;
		String url = "http://localhost:" + port + "/api/issue/" + issueId + "/comment";

		//then
		webTestClient.get()
			.uri(url)
			.header("Cookie", cookie)
			.exchange()
			.expectStatus()
			.isOk();
	}

	@Test
	void 이슈에_댓글을_추가한다() {
		String url = "http://localhost:" + port + "/api/issue/comment/add";
		CommentCreateRequestDto dto = CommentCreateRequestDto.builder()
			.issueId(1L)
			.userGitHubId("jypthemiracle")
			.content("hello world")
			.build();
		//then
		webTestClient.put()
			.uri(url)
			.header("Cookie", cookie)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(dto), CommentCreateRequestDto.class)
			.exchange()
			.expectStatus()
			.isOk();
	}

	@Test
	void 이슈에_댓글을_수정한다() {
		String url = "http://localhost:" + port + "/api/issue/comment/update";
		CommentUpdateRequestDto dto = CommentUpdateRequestDto.builder()
			.issueId(1L)
			.commentId(1L)
			.userGitHubId("guswns1659")
			.content("i love java")
			.photos(Arrays.asList(Photo.ofUrl("naver.com"), Photo.ofUrl("sigrid.com")))
			.emojis(Arrays.asList(Emoji.CONFUSED, Emoji.EYES))
			.build();
		webTestClient.put()
			.uri(url)
			.header("Cookie", cookie)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(dto), CommentUpdateRequestDto.class)
			.exchange()
			.expectStatus()
			.isOk();
		List<Comment> comments = issueService.findCommentsByIssueId(1L).collectSortedList().block();
		assertThat(comments.get(0).getContent()).isEqualTo("i love java");
	}

	@Test
	void 이슈에_댓글을_삭제한다() {
		//given
		String url = "http://localhost:" + port + "/api/issue/comment/delete";
		CommentDeleteRequestDto dto = CommentDeleteRequestDto.builder()
			.issueId(1L)
			.commentId(1L)
			.userGitHubId("guswns1659")
			.build();
		webTestClient.method(HttpMethod.DELETE)
			.uri(url)
			.header("Cookie", cookie)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(dto), CommentDeleteRequestDto.class)
			.exchange()
			.expectStatus()
			.isOk();
		//when
		List<Comment> commentList = issueService.findCommentsByIssueId(1L).collectSortedList().block();
		Comments comments = Comments.ofComments(commentList);
		//then
		assertThatThrownBy(
			() -> comments.findCommentById(1L)
		).isInstanceOf(IllegalArgumentException.class);
	}
}
