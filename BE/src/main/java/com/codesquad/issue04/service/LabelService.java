package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.domain.label.LabelRepository;
import com.codesquad.issue04.web.dto.response.label.LabelDetailResponseDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewResponseDtos;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelService {

	private final LabelRepository labelRepository;

	protected Label findLabelById(Long labelId) {
		return labelRepository.findById(labelId)
			.orElseThrow(() -> new IllegalArgumentException("label not found id: " + labelId));
	}

	protected List<Label> findAllLabels() {
		return labelRepository.findAll();
	}

	private List<LabelOverviewDto> findAllLabelsOverview() {
		List<Label> labels = findAllLabels();
		return labels.stream()
			.map(LabelOverviewDto::of)
			.collect(Collectors.toList());
	}

	public LabelDetailResponseDto findLabelDetailById(Long labelId) {
		return LabelDetailResponseDto.of(findLabelById(labelId));
	}

	public LabelOverviewResponseDtos getLabelOverviews() {
		return LabelOverviewResponseDtos.builder()
			.allData(findAllLabelsOverview())
			.build();
	}
}
