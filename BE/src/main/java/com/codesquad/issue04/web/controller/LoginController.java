package com.codesquad.issue04.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.service.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;

    @GetMapping("/api/authorization")
    public ResponseEntity<HttpStatus> login(HttpServletResponse response,
            @AuthenticationPrincipal OAuth2User oAuth2User) {

        String userId = oAuth2User.getAttribute("login");
        String jwt = jwtService.createJwt(userId);

        Cookie cookie = new Cookie("userId", userId);
        cookie.setMaxAge(60*60*6);
        response.addCookie(cookie);
        response.setHeader("Location", "token:" + jwt);

        return new ResponseEntity<>(HttpStatus.FOUND);
    }
}
