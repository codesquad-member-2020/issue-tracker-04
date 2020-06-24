package com.codesquad.issue04.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.domain.label.Label;
import com.codesquad.issue04.service.LabelService;
import com.codesquad.issue04.web.dto.request.LabelCreateRequestDto;
import com.codesquad.issue04.web.dto.request.LabelUpdateRequestDto;
import com.codesquad.issue04.web.dto.response.label.LabelOverviewResponseDtos;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class LabelController {

    private final LabelService labelService;

    @GetMapping("v1/allLabels")
    public LabelOverviewResponseDtos getAllLabels() {
        return labelService.getLabelOverviews();
    }

    @PostMapping("v1/label")
    public Label createNewLabel(@RequestBody LabelCreateRequestDto labelCreateRequestDto) {
        return labelService.createNewLabel(labelCreateRequestDto);
    }

    @PutMapping("v1/label")
    public Label updateExistingLabel(@RequestBody LabelUpdateRequestDto labelUpdateRequestDto) {
        return labelService.updateExistingLabel(labelUpdateRequestDto);
    }

    @DeleteMapping("v1/label/{labelId}")
    public Label deleteLabel(@PathVariable Long labelId) {
        return labelService.deleteExistingLabel(labelId);
    }
}
