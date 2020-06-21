package com.codesquad.issue04.web.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.codesquad.issue04.web.dto.response.milestone.MilestoneResponseDtos;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MilestoneControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("전체 마일스톤을 응답하는 테스트")
    @Test
    void 전체_마일스톤을_응답한다() {
        String url = "http://localhost:" + port + "/api/v1/allMilestones";

        EntityExchangeResult<MilestoneResponseDtos> entityExchangeResult = webTestClient.get()
            .uri(url)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(MilestoneResponseDtos.class)
            .returnResult();

        assertThat(entityExchangeResult.getResponseBody()).isInstanceOf(MilestoneResponseDtos.class);
        assertThat(entityExchangeResult.getResponseBody().getAllData().get(0).getTitle()).isEqualTo("1차 배포");
    }
}
