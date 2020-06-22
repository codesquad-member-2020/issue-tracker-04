package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.web.dto.request.LabelCreateRequestDto;
import com.codesquad.issue04.web.dto.request.LabelDeleteRequestDto;
import com.codesquad.issue04.web.dto.request.LabelUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.label.LabelDetailResponseDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewResponseDtos;

@SpringBootTest
public class LabelServiceTest {

	@Autowired
	private LabelService labelService;

	@DisplayName("라벨이 불러와진다.")
	@Test
	void 라벨이_불러와진다() {
		assertThat(labelService.findLabelById(1L)).isInstanceOf(Label.class);
	}

	@Test
	void 라벨_전체가_불러와진다() {
		assertThat(labelService.findAllLabels()).isInstanceOf(List.class);
		assertThat(labelService.findAllLabels().get(0)).isInstanceOf(Label.class);
	}

	@Test
	void 라벨_개요가_불러와진다() {
		assertThat(labelService.findAllLabelsOverview()).isInstanceOf(List.class);
		assertThat(labelService.findAllLabelsOverview().get(0)).isInstanceOf(LabelOverviewDto.class);
		assertThat(labelService.getLabelOverviews()).isInstanceOf(LabelOverviewResponseDtos.class);
	}

	@Test
	void 라벨_구체_정보가_불러와진다() {
		assertThat(labelService.findLabelDetailById(1L)).isInstanceOf(LabelDetailResponseDto.class);
	}

	@Transactional
	@DisplayName("라벨 하나가 추가된다.")
	@CsvSource({"BE, #F2DUS3, 백엔드 작업이슈"})
	@ParameterizedTest
	void 라벨_하나가_추가된다(String title, String color, String description) {
		LabelCreateRequestDto dto = new LabelCreateRequestDto(title, color, description);
		labelService.createNewLabel(dto);
		LabelDetailResponseDto savedLabelDto = labelService.findLatestLabel();
		assertAll(
			() -> assertThat(savedLabelDto.getTitle()).isEqualTo(title),
			() -> assertThat(savedLabelDto.getColor()).isEqualTo(color),
			() -> assertThat(savedLabelDto.getDescription()).isEqualTo(description)
		);
	}

	@Transactional
	@DisplayName("라벨 하나가 수정된다.")
	@CsvSource({"1, 백엔드, #D3CV8K, 백엔드 2차 배포"})
	@ParameterizedTest
	void 라벨_하나가_수정된다(Long id, String title, String color, String description) {
		LabelUpdateRequestDto dto = new LabelUpdateRequestDto(id, title, color, description);
		labelService.updateExistingLabel(dto);
		Label afterUpdatedLabel = labelService.findLabelById(id);
		assertAll(
			() -> assertThat(afterUpdatedLabel.getTitle()).isEqualTo(title),
			() -> assertThat(afterUpdatedLabel.getColor()).isEqualTo(color),
			() -> assertThat(afterUpdatedLabel.getDescription()).isEqualTo(description)
		);
	}

	@Transactional
	@DisplayName("라벨 하나가 삭제된다.")
	@ValueSource(longs = 1)
	@ParameterizedTest
	void 라벨_하나가_삭제된다(Long id) {
		LabelDeleteRequestDto dto = new LabelDeleteRequestDto(id);
		labelService.deleteExistingLabel(dto);
		assertThatThrownBy(
			() -> labelService.findLabelById(id)
		).isInstanceOf(IllegalArgumentException.class);
	}
}
