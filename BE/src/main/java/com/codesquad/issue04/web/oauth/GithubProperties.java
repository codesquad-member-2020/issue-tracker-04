package com.codesquad.issue04.web.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("github")
@Getter
@Setter
public class GithubProperties {

    public String accessTokenUrl;
    public String clientId;
    public String clientSecret;
    public String redirectUrl;
    public String userEmailRequestUrl;
}
