package com.codesquad.issue04.web.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.web.dto.request.LabelCreateRequestDto;
import com.codesquad.issue04.web.dto.request.LabelUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewResponseDtos;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class LabelControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Transactional
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

    @Transactional
    @DisplayName("라벨을 추가한 후 저장된 라벨을 응답하는 테스트")
    @CsvSource({"라벨, #9999, 라벨입니다"})
    @ParameterizedTest
    void 라벨을_추가한다(String title, String color, String description) {
        String url = "http://localhost:" + port + "/api/v1/label";

        LabelCreateRequestDto labelCreateRequestDto = new LabelCreateRequestDto(title, color, description);

        Label label = webTestClient.post()
            .uri(url)
            .body(Mono.just(labelCreateRequestDto), LabelCreateRequestDto.class)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(com.codesquad.issue04.domain.label.Label.class)
            .returnResult()
            .getResponseBody();

        assertThat(label.getTitle()).isEqualTo(title);
        assertThat(label.getColor()).isEqualTo(color);
        assertThat(label.getDescription()).isEqualTo(description);
    }

    @Transactional
    @DisplayName("라벨을 수정한 후 수정된 라벨을 응답하는 테스트")
    @CsvSource({"1, 라벨, #9999, 라벨입니다"})
    @ParameterizedTest
    void 라벨을_수정한다(String id, String title, String color, String description) {
        String url = "http://localhost:" + port + "/api/v1/label";

        Long ll = Long.parseLong(id);
        LabelUpdateRequestDto labelUpdateRequestDto = new LabelUpdateRequestDto((Long.parseLong(id)), title, color, description);

        Label updatedLabel = webTestClient.put()
            .uri(url)
            .body(Mono.just(labelUpdateRequestDto), LabelUpdateRequestDto.class)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(Label.class)
            .returnResult()
            .getResponseBody();

        assertThat(updatedLabel.getId()).isEqualTo((Long.parseLong(id)));
        assertThat(updatedLabel.getTitle()).isEqualTo(title);
        assertThat(updatedLabel.getColor()).isEqualTo(color);
        assertThat(updatedLabel.getDescription()).isEqualTo(description);
    }

    @Transactional
    @DisplayName("라벨을 삭제하고 삭제된 라벨을 응답하는 테스트")
    @CsvSource({"1, BE-배포, #FF5733, 백엔드 배포 라벨"})
    @ParameterizedTest
    void 라벨을_삭제한다(String id, String title, String color, String description) {
        String url = "http://localhost:" + port + "/api/v1/label/" + Long.parseLong(id);

        Label deletedLabel = webTestClient.delete()
            .uri(url)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(Label.class)
            .returnResult()
            .getResponseBody();

        assertThat(deletedLabel.getId()).isEqualTo(Long.parseLong(id));
        assertThat(deletedLabel.getTitle()).isEqualTo(title);
        assertThat(deletedLabel.getColor()).isEqualTo(color);
        assertThat(deletedLabel.getDescription()).isEqualTo(description);
    }
}
