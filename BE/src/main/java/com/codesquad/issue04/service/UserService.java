package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.domain.user.UserRepository;
import com.codesquad.issue04.web.dto.response.user.AllAuthorsResponseDto;
import com.codesquad.issue04.web.dto.response.user.AuthorDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AllAuthorsResponseDto getAllAuthors() {
        return AllAuthorsResponseDto.builder()
            .allData(findAllAuthors())
            .build();
    }

    private List<AuthorDto> findAllAuthors() {
        List<RealUser> users = userRepository.findAll();
        List<RealUser> authors = users.stream()
            .filter(RealUser::hasIssue)
            .collect(Collectors.toList());

        return authors.stream()
            .map(AuthorDto::of)
            .collect(Collectors.toList());
    }
}
