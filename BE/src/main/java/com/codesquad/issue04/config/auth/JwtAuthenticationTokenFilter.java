package com.codesquad.issue04.config.auth;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    @Value("${jwt.token.header}")
    private String tokenHeader;
    private Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

    private final String jwt;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, String jwt) {
        super(authenticationManager);
        this.jwt = jwt;
    }

    // endpoint every request hit with authorization
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        // String jwtToken = request.getHeader("Authorization");
        log.info("jwt : {}", this.jwt);
        // Continue filter execution
        chain.doFilter(request, response);
    }
}
