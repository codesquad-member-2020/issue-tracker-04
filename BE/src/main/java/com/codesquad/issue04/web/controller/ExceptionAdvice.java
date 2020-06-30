package com.codesquad.issue04.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private String catchException(IllegalArgumentException e) {
		return e.getMessage();
	}
}
