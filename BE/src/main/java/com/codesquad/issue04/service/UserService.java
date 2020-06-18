package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.user.NullUser;
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
            .filter(RealUser::hasOwnedIssues)
            .collect(Collectors.toList());

        return authors.stream()
            .map(AuthorDto::of)
            .collect(Collectors.toList());
    }

    public List<Issue> getAllAssignedIssues() {

        String userId = "guswns1659";
        RealUser realUser = userRepository.findUserByGithubId(userId)
            .orElse(NullUser.of());

        return realUser.getAssignedIssues();
    }

}
