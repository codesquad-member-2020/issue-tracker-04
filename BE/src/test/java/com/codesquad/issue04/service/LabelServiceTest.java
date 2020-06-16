package com.codesquad.issue04.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codesquad.issue04.domain.label.Label;

@SpringBootTest
public class LabelServiceTest {

	@Autowired
	private LabelService labelService;

	@DisplayName("라벨이 불러와진다.")
	@Test
	void 라벨이_불러와진다() {
		assertThat(labelService.findLabelById(1L)).isInstanceOf(Label.class);
	}
}
