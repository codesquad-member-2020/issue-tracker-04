package com.codesquad.issue04.web.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestBody {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("code")
    private String code;

    public RequestBody() {}

    @Builder
    public RequestBody(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }
}