package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.milestone.MilestoneRepository;
import com.codesquad.issue04.web.dto.response.milestone.MilestoneDto;
import com.codesquad.issue04.web.dto.response.milestone.MilestoneResponseDtos;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {

	private final MilestoneRepository milestoneRepository;

	public MilestoneResponseDtos getMilestoneOverviews() {
		return MilestoneResponseDtos.builder()
			.allData(getAllMilestones())
			.build();
	}

	protected List<MilestoneDto> getAllMilestones() {
		List<Milestone> milestones = findAllMilestones();
		return milestones.stream()
			.map(MilestoneDto::of)
			.collect(Collectors.toList());
	}

	protected List<Milestone> findAllMilestones() {
		return milestoneRepository.findAll();
	}

	protected Milestone findMilestoneById(Long labelId) {
		return milestoneRepository.findById(labelId)
			.orElseThrow(() -> new IllegalArgumentException("label not found id: " + labelId));
	}

	public Milestone createMilestone(Milestone milestone) {
		return milestoneRepository.save(milestone);
	}
}
