package com.codesquad.issue04.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties("github")
public class GithubProperties {

	private String accessTokenUrl;
	private String clientId;
	private String clientSecret;
	private String redirectUrl;
	private String userEmailRequestUrl;
}
