package com.codesquad.issue04.web.dto.request;

import com.codesquad.issue04.utils.GithubProperties;
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
public class AccessTokenRequestDto {

	@JsonProperty("client_id")
	private String clientId;
	@JsonProperty("client_secret")
	private String clientSecret;
	private String code;
	@JsonProperty("redirect_url")
	private String redirectUrl;

	public static AccessTokenRequestDto of(GithubProperties githubProperties, String code) {
		return AccessTokenRequestDto.builder()
			.clientId(githubProperties.getClientId())
			.clientSecret(githubProperties.getClientSecret())
			.redirectUrl(githubProperties.getRedirectUrl())
			.code(code)
			.build();
	}
}
