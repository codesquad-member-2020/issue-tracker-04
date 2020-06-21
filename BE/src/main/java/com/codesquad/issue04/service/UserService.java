package com.codesquad.issue04.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.user.NullUser;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.domain.user.UserRepository;
import com.codesquad.issue04.web.dto.response.user.AllAuthorsResponseDto;
import com.codesquad.issue04.web.dto.response.user.AuthorDto;
import com.codesquad.issue04.web.oauth.GithubUser;
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
        RealUser realUser = userRepository.findByGithubId(userId)
            .orElse(NullUser.of());

        return realUser.getAssignedIssues();
    }

    public List<RealUser> findUsersHasIssues() {
        List<RealUser> users = userRepository.findAll();

        return users.stream()
            .filter(RealUser::hasAssignedIssues)
            .collect(Collectors.toList());
    }

    @Transactional
    public void save(GithubUser githubUser) {
        RealUser user = userRepository.findByGithubId(githubUser.getUserId()).orElse(NullUser.of());
        if (user.isNil()) {
            userRepository.save(RealUser.of(githubUser));
        }
    }
}
