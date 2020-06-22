package com.codesquad.issue04.domain.issue;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.codesquad.issue04.domain.issue.vo.firstcollection.Labels;
import com.codesquad.issue04.domain.milestone.Milestone;
import com.codesquad.issue04.domain.user.RealUser;

@SpringBootTest
public class IssueTest {

	private RealUser user;

	@BeforeEach
	void setUp() {
		this.user = RealUser.builder()
			.id(1L)
			.githubId("guswns1659")
			.image("image")
			.name("Jack")
			.ownedIssues(Collections.emptyList())
			.build();
	}

	@DisplayName("Null인 필드의 빈 객체를 반환한다.")
	@Test
	void 이슈필드의_Null_테스트() {

		Issue issue = Issue.builder()
			.id(1L)
			.comments(null)
			.labels(Labels.ofNullLabels())
			.milestone(new Milestone())
			.title("title")
			.user(this.user)
			.build();


		assertThat(issue.getComments()).isNotNull();

	}
}
