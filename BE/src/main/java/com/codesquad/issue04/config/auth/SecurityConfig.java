package com.codesquad.issue04.config.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			.authorizeRequests()
			//TODO: 스프링 시큐리티 테스트를 적용한다.
			.antMatchers("/", "/api/**", "/css/**", "/images/**", "/js/**").permitAll()
			// .antMatchers("/api/v1/**").hasRole(RO)
			.anyRequest().authenticated()
			.and()
				.logout()
					.logoutSuccessUrl("/")
			.and()
				.oauth2Login()
            		.defaultSuccessUrl("/api/authorization")
					.failureUrl("/")
					.userInfoEndpoint()
						.userService(customOAuth2UserService);
	}
}
