package com.codesquad.issue04.web.controller;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.web.dto.request.MilestoneCreateRequestDto;
import com.codesquad.issue04.web.dto.request.MilestoneUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.milestone.MilestoneDto;
import com.codesquad.issue04.web.dto.response.milestone.MilestoneResponseDtos;
import reactor.core.publisher.Mono;

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

    @Transactional
    @DisplayName("마일스톤을 추가하고 추가된 마일스톤을 응답하는 테스트")
    @CsvSource({"마일스톤 제목, 2020-03-03, 마일스톤 설명"})
    @ParameterizedTest
    void 마일스톤을_생성한다(String title, String dueDate, String description) {
        String url = "http://localhost:" + port + "/api/v1/milestone";

        MilestoneCreateRequestDto milestoneCreateRequestDto =
            new MilestoneCreateRequestDto(title, LocalDate.parse(dueDate), description);

        Milestone milestone = webTestClient.post()
            .uri(url)
            .body(Mono.just(milestoneCreateRequestDto), MilestoneCreateRequestDto.class)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(Milestone.class)
            .returnResult()
            .getResponseBody();

        assertThat(milestone.getTitle()).isEqualTo(title);
        assertThat(milestone.getDueDate()).isEqualTo(LocalDate.parse(dueDate));
        assertThat(milestone.getDescription()).isEqualTo(description);
    }

    @Transactional
    @DisplayName("마일스톤을 수정하고 수정된 마일스톤을 응답하는 테스트")
    @CsvSource({"1, 마일스톤 수정제목, 2020-02-02, 마일스톤 수정설명"})
    @ParameterizedTest
    void 마일스톤을_수정한다(String id, String title, String dueDate, String description) {
        String url = "http://localhost:" + port + "/api/v1/milestone";

        MilestoneUpdateRequestDto milestoneUpdateRequestDto = new MilestoneUpdateRequestDto(Long.parseLong(id), title, LocalDate
            .parse(dueDate), description);

        MilestoneDto milestoneDto = webTestClient.put()
            .uri(url)
            .body(Mono.just(milestoneUpdateRequestDto), MilestoneUpdateRequestDto.class)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(MilestoneDto.class)
            .returnResult()
            .getResponseBody();

        assertThat(milestoneDto.getId()).isEqualTo(Long.parseLong(id));
        assertThat(milestoneDto.getTitle()).isEqualTo(title);
        assertThat(milestoneDto.getDueDate()).isEqualTo(LocalDate.parse(dueDate));
        assertThat(milestoneDto.getDescription()).isEqualTo(description);
    }

    @Transactional
    @DisplayName("마일스톤을 삭제하고 삭제된 마일스톤을 응답하는 테스트")
    @CsvSource({"6, 마일스톤 제목, 2020-03-03, 마일스톤 설명"})
    @ParameterizedTest
    void 마일스톤을_삭제한다(String id, String title, String dueDate, String description) {
        String url = "http://localhost:" + port + "/api/v1/milestone/" + Long.parseLong(id);

        Milestone milestone = webTestClient.delete()
            .uri(url)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody(Milestone.class)
            .returnResult()
            .getResponseBody();

        assertThat(milestone.getId()).isEqualTo(Long.parseLong(id));
        assertThat(milestone.getTitle()).isEqualTo(title);
        assertThat(milestone.getDueDate()).isEqualTo(LocalDate.parse(dueDate));
        assertThat(milestone.getDescription()).isEqualTo(description);
    }
}
