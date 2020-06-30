package com.codesquad.issue04.web.dto.request;

import com.codesquad.issue04.domain.issue.vo.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FilterParamRequestDto {

    private Status status;
    private String role;
    private String option;
    private String value;
}
