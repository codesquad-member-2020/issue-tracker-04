package com.codesquad.issue04.web.dto.response.issue;

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
public class IssueOverviewResponseDtos implements ResponseDto {

	private List<IssueOverviewDto> allData;

	public static IssueOverviewResponseDtos of (List<IssueOverviewDto> allData) {
		return IssueOverviewResponseDtos.builder()
			.allData(allData)
			.build();
	}
}
