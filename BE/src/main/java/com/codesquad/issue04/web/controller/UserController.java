package com.codesquad.issue04.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.issue04.service.UserService;
import com.codesquad.issue04.web.dto.response.user.AllAssigneeResponseDto;
import com.codesquad.issue04.web.dto.response.user.AllAuthorsResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @GetMapping("v1/allAuthors")
    public AllAuthorsResponseDto getAllAuthors() {
        return userService.getAllAuthors();
    }

    @GetMapping("v1/allAssignees")
    public AllAssigneeResponseDto getAllAssignee() {
        return userService.getAllAssignee();
    }
}
