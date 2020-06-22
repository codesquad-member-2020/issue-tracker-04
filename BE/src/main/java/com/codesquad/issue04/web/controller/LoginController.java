package com.codesquad.issue04.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.service.JwtService;
import com.codesquad.issue04.service.LoginService;
import com.codesquad.issue04.service.UserService;
import com.codesquad.issue04.web.oauth.Github;
import com.codesquad.issue04.web.oauth.GithubUser;
import com.codesquad.issue04.web.oauth.Oauth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserService userService;

    private final Integer EXPIRE_TIME = 60*60*6;

    @GetMapping("/callback")
    public ResponseEntity<HttpStatus> oauthCallback(@Param("code") String code, HttpServletResponse response) {
        Github github = loginService.requestAccessToken(code);
        log.info("Github AccessToken, TokenType, Scope Data : {}", github);
        GithubUser githubUser = loginService.requestUserInfo(github.getAccessToken());
        log.info("Github UserId : {}", githubUser);

        userService.save(githubUser);
        String jwt = jwtService.createJwt(githubUser.getUserId());

        Cookie cookie = new Cookie(String.valueOf(Oauth.USER_ID), githubUser.getUserId());
        cookie.setMaxAge(EXPIRE_TIME);
        response.addCookie(cookie);
        response.setHeader(String.valueOf(Oauth.HEADER_LOCATION), Oauth.MOBILE_REDIRECT_URL + jwt);
        return new ResponseEntity(HttpStatus.FOUND);
    }
}