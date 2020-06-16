package com.codesquad.issue04.web.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codesquad.issue04.web.dto.response.issue.IssueOverviewResponseDtos;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void 전체_요약_이슈를_요청해서_응답한다() {

		// given
		String url = "http://localhost:" + port + "/api/v1/issues";

		// when
		ResponseEntity<IssueOverviewResponseDtos> responseEntity
			= restTemplate.getForEntity(url, IssueOverviewResponseDtos.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isInstanceOf(IssueOverviewResponseDtos.class);
	}
}
