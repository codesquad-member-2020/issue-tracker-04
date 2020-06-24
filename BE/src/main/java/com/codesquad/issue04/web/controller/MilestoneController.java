package com.codesquad.issue04.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.service.MilestoneService;
import com.codesquad.issue04.web.dto.response.milestone.MilestoneResponseDtos;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/allMilestones")
public class MilestoneController {

	private final MilestoneService milestoneService;

	@GetMapping
	public Mono<MilestoneResponseDtos> getAllMilestones() {
		return Mono.just(milestoneService.getMilestoneOverviews());
	}
}
