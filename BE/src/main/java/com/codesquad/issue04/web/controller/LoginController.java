package com.codesquad.issue04.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.codesquad.issue04.service.LoginService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// private final LoginService loginService;

	// @GetMapping("/login/oauth2/code/github")
	// public ResponseEntity<Void> login(@RequestParam("code") String code,
	// 	HttpServletResponse response) {
	// 	return loginService.login(code, response);
	// }
}
