package com.codesquad.issue04.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@ElementCollection
	@CollectionTable(name = "emoji", joinColumns = @JoinColumn(name = "comment_id"))
	private List<Emoji> emojis;

	@ElementCollection
	@CollectionTable(name = "photo", joinColumns = @JoinColumn(name = "comment_id"))
	private List<Photo> photos;

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
