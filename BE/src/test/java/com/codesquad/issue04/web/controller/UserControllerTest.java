package com.codesquad.issue04.web.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.codesquad.issue04.web.dto.response.user.AllAssigneeResponseDto;
import com.codesquad.issue04.web.dto.response.user.AllAuthorsResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "1500")
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("작성자 전체를 요청하는 API")
    @Test
    void 작성자_전체를_응답한다() {
        String url = "http://localhost:" + port + "/api/v1/allAuthors";

        webTestClient.get()
            .uri(url)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(AllAuthorsResponseDto.class)
            .consumeWith(response ->
                assertThat(response.getResponseBody()).isInstanceOf(AllAuthorsResponseDto.class))
            .consumeWith(response ->
                assertThat(response.getResponseBody().getAllData().get(0).getGithubId()).isEqualTo("guswns1659"));
    }

    @DisplayName("할당된 이슈가 있는 담당자를 가져오는 API")
    @Test
    void 담당자_전체를_응답한다() {
        String url = "http://localhost:" + port + "/api/v1/allAssignees";

        webTestClient.get()
            .uri(url)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(AllAssigneeResponseDto.class)
            .consumeWith(response ->
                assertThat(response.getResponseBody()).isInstanceOf(AllAssigneeResponseDto.class))
            .consumeWith(response ->
                assertThat(response.getResponseBody().getAllData().get(0).getGithubId()).isEqualTo("guswns1659"));
    }
}
