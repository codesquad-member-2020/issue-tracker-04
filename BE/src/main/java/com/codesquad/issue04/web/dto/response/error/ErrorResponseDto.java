package com.codesquad.issue04.web.dto.response.error;

import java.io.Serializable;

import com.codesquad.issue04.web.dto.response.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto implements ResponseDto, Serializable {
	private int code;
	private String message;

	public ErrorResponseDto(int code, Exception exception) {
		this.code = code;
		this.message = exception.getMessage();
	}
}
