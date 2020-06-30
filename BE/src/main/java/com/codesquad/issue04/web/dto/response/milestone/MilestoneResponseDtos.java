package com.codesquad.issue04.web.dto.response.milestone;

import java.util.List;

import com.codesquad.issue04.web.dto.response.ResponseDto;
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
public class MilestoneResponseDtos implements ResponseDto {
	private List<MilestoneDto> allData;
}
