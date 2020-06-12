package com.codesquad.issue04.domain.issue;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.codesquad.issue04.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "issue")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@ElementCollection(targetClass = Emoji.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "emoji")
	@Column(name = "name")
	private List<Emoji> emojis;

	@ElementCollection
	@CollectionTable(name = "photo", joinColumns = @JoinColumn(name = "comment_id"))
	@Column(name = "url")
	private List<Photo> photos = new ArrayList<>();

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
	private User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_id"))
	private Issue issue;

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
