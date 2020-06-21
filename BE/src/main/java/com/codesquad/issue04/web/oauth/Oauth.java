package com.codesquad.issue04.web.oauth;

public enum Oauth {
    USER_ID("userId"),  HEADER_LOCATION("Location"),
    MOBILE_REDIRECT_URL("issue04://?token="),
    HEADER_ACCEPT("Accept"), HEADER_MEDIA_TYPE("application/json"),
    OAUTH_URL_SERVER("https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://localhost:8080/callback&scope=user");

    Oauth(String value) {
    }
}
