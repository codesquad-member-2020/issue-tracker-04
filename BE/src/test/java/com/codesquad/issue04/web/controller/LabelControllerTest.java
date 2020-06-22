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

import com.codesquad.issue04.web.dto.response.label.LabelOverviewResponseDtos;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class LabelControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("전체 라벨을 응답하는 테스트")
    @Test
    void 전체_라벨을_가져온다() {
        String url = "http://localhost:" + port + "/api/v1/allLabels";

        webTestClient.get()
            .uri(url)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(LabelOverviewResponseDtos.class)
            .consumeWith(response ->
                assertThat(response.getResponseBody()).isInstanceOf(LabelOverviewResponseDtos.class))
            .consumeWith(response ->
                assertThat(response.getResponseBody().getAllData().get(0).getColor()).isEqualTo("#FF5733"));
    }
}
