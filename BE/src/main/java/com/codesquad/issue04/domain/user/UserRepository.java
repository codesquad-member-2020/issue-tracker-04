package com.codesquad.issue04.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RealUser, Long> {

	Optional<RealUser> findByGithubId(String githubId);
}
