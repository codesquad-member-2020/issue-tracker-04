package com.codesquad.issue04.config.auth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.codesquad.issue04.config.dto.OAuthAttributes;
import com.codesquad.issue04.config.dto.SessionUser;
import com.codesquad.issue04.domain.user.User;
import com.codesquad.issue04.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributename = userRequest.getClientRegistration().getProviderDetails()
			.getUserInfoEndpoint().getUserNameAttributeName();
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributename, oAuth2User.getAttributes());
		User user = saveOrUpdate(attributes);
		httpSession.setAttribute("user", new SessionUser(user));

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
			attributes.getAttributes(),
			attributes.getNameAttributeKey());
	}

	private User saveOrUpdate(OAuthAttributes attributes) {

		User user = userRepository.findByGithubId(attributes.getGithubId())
			.map(entity -> entity.update(attributes.getName(), attributes.getImage()))
			.orElse(attributes.toEntity());

		return userRepository.save(user);
	}
}
