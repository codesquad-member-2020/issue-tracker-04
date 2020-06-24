package com.codesquad.issue04.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codesquad.issue04.web.oauth.Github;
import com.codesquad.issue04.web.oauth.GithubProperties;
import com.codesquad.issue04.web.oauth.GithubUser;
import com.codesquad.issue04.web.oauth.Oauth;
import com.codesquad.issue04.web.oauth.RequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final GithubProperties githubProperties;

    public Github requestAccessToken(String code) {
        RequestBody requestBody = RequestBody.builder()
            .clientId(githubProperties.clientId)
            .clientSecret(githubProperties.clientSecret)
            .code(code)
            .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set(Oauth.HEADER_ACCEPT.value(), Oauth.HEADER_MEDIA_TYPE.value());
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Github> responseEntity = new RestTemplate().postForEntity(githubProperties.accessTokenUrl, httpEntity, Github.class);
        return responseEntity.getBody();
    }

    public GithubUser requestUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<GithubUser> responseEntity = new RestTemplate().exchange(githubProperties.userEmailRequestUrl, HttpMethod.GET, httpEntity, GithubUser.class);
        return githubUserValidation(responseEntity.getBody());
    }

    private GithubUser githubUserValidation(GithubUser githubUser) {
        if (githubUser.nameIsNull()) {
            githubUser.fixName();
        }
        return githubUser;
    }
}