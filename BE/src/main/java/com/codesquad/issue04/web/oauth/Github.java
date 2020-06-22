package com.codesquad.issue04.web.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Github {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

    public Github() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAuthorization() {
        return tokenType + " " + accessToken;
    }
}