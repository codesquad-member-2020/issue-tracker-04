package com.codesquad.issue04.domain.entity;

import com.codesquad.issue04.domain.firstcollections.Emojis;
import com.codesquad.issue04.domain.firstcollections.Photos;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Embedded
	private Emojis emojis;
	@Embedded
	private Photos photos;
	private String githubId;
	private Long issueId;

	public String getFormattedCreatedDate() {
		return formattedDate(createdAt, "YYYY-MM-SS HH:mm:ss");
	}

	public String getFormattedModifiedDate() {
		return formattedDate(updatedAt, "YYYY-MM-SS HH:mm:ss");
	}

	private String formattedDate(LocalDateTime localDateTime, String format) {
		if (localDateTime == null) {
			return "";
		}
		return localDateTime.format(DateTimeFormatter.ofPattern(format));
	}

}
