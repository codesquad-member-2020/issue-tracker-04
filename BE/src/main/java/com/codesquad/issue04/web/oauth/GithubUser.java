package com.codesquad.issue04.web.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class GithubUser {

    @JsonProperty("login")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar_url")
    private String image;

    public void fixName() {
        this.name = this.userId;
    }

    public boolean nameIsNull() {
        return this.name == null;
    }
}