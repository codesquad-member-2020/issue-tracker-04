// package com.codesquad.issue04.service;
//
// import javax.servlet.http.HttpServletResponse;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;
//
// import com.codesquad.issue04.utils.GithubProperties;
// import com.codesquad.issue04.web.dto.request.AccessTokenRequestDto;
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
public class LoginService {
//
// 	private final RestTemplate restTemplate;
// 	private final GithubProperties githubProperties;
// 	private final Logger logger = LoggerFactory.getLogger(LoginService.class);
//
// 	public ResponseEntity<Void> login(String code, HttpServletResponse response) {
//
// 		try {
// 			AccessTokenRequestDto accessTokenRequestDto
// 				= AccessTokenRequestDto.of(githubProperties, code);
// 			String rawAccessToken = restTemplate.postForObject(githubProperties.getAccessTokenUrl()
// 				, accessTokenRequestDto, String.class);
// 			String accessToken = parse(rawAccessToken);
// 			String userId = requestUserIdToGithubWithAccessToken(githubProperties, accessToken);
//
// 			return new ResponseEntity<>(HttpStatus.OK);
//
// 		} catch (Exception e) {
// 			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
// 		}
// 	}
//
// 	private String requestUserIdToGithubWithAccessToken(GithubProperties githubProperties, String accessToken) {
// 		String userEmailRequestUrl = githubProperties.getUserEmailRequestUrl();
//
// 		HttpHeaders httpHeaders = new HttpHeaders();
// 		httpHeaders.set("Authorization", "token " + accessToken);
// 		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
// 		ResponseEntity<String> responseEntity
// 			= restTemplate.exchange(userEmailRequestUrl, HttpMethod.GET, entity, String.class);
//
// 		String userId = responseEntity.getBody().split(",")[0].split(":")[1].split("@")[0];
// 		if (userId.contains("\"")) {
// 			userId = userId.replaceAll("\"", "");
// 		}
// 		return userId;
// 	}
//
// 	private String parse(String rawAccessToken) {
// 		String[] splitTokens = rawAccessToken.split("&");
// 		String[] splitTokens2 = splitTokens[0].split("=");
// 		return splitTokens2[1];
// 	}
}
