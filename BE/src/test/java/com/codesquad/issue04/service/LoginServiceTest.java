package com.codesquad.issue04.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codesquad.issue04.utils.GithubProperties;
import com.codesquad.issue04.web.dto.request.AccessTokenRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginServiceTest {

	@Autowired
	private GithubProperties githubProperties;

	@DisplayName("AccessTokenRequestDto가 생성되는 지 확인한다.")
	@Test
	void accessTokenRequestDto을_생성한다() {

		// given
		String code = "fjksdajfksdjakel";
		// when
		AccessTokenRequestDto accessTokenRequestDto
			= AccessTokenRequestDto.of(githubProperties, code);
		// then
		assertThat(accessTokenRequestDto).isInstanceOf(AccessTokenRequestDto.class);
		assertThat(accessTokenRequestDto.getClientId()).isEqualTo(githubProperties.getClientId());
	}
}
