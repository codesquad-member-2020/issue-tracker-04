package com.codesquad.issue04.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.service.LabelService;
import com.codesquad.issue04.web.dto.request.label.LabelCreateRequestDto;
import com.codesquad.issue04.web.dto.request.label.LabelUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewResponseDtos;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping("allLabels")
    public LabelOverviewResponseDtos getAllLabels() {
        return labelService.getLabelOverviews();
    }

    @PostMapping("label")
    public Label createNewLabel(@RequestBody LabelCreateRequestDto labelCreateRequestDto) {
        return labelService.createNewLabel(labelCreateRequestDto);
    }

    @PutMapping("label")
    public Label updateExistingLabel(@RequestBody LabelUpdateRequestDto labelUpdateRequestDto) {
        return labelService.updateExistingLabel(labelUpdateRequestDto);
    }

    @DeleteMapping("label/{labelId}")
    public Label deleteLabel(@PathVariable Long labelId) {
        return labelService.deleteExistingLabel(labelId);
    }
}
