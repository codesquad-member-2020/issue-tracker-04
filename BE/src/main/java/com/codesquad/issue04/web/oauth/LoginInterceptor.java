package com.codesquad.issue04.web.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.codesquad.issue04.service.JwtService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        final String token = request.getHeader(String.valueOf(Oauth.AUTHORIZATION));
        log.info("token >> {}", token);

        return jwtService.isValidToken(token);
    }
}
