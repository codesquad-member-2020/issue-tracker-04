package com.codesquad.issue04.domain.issue.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.codesquad.issue04.domain.issue.Issue;
import com.codesquad.issue04.domain.user.RealUser;
import com.codesquad.issue04.web.dto.request.CommentCreateRequestDto;
import com.codesquad.issue04.web.dto.request.CommentUpdateRequestDto;
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
// @Embeddable
public class Comment implements Serializable {

	@javax.persistence.Id
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
	private List<Photo> photos;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
	private RealUser user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_id"))
	private Issue issue;

	private Comment(String content, List<Emoji> emojis, List<Photo> photos, RealUser user, Issue issue) {
		this.content = content;
		this.emojis = emojis;
		this.photos = photos;
		this.user = user;
		this.issue = issue;
	}

	public static Comment ofDto(CommentCreateRequestDto dto, RealUser user, Issue issue) {
		return new Comment(dto.getContent(), dto.getEmojis(), dto.getPhotos(), user, issue);
	}

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

	public Long getUserId() {
		return this.user.getId();
	}

	public Comment updateComment(CommentUpdateRequestDto dto) {
		this.content = dto.getContent();
		this.photos = dto.getMockPhotos();
		this.emojis = dto.getMockEmojis();
		return this;
	}

	public boolean doesMatchId(Long commentId) {
		return this.id.equals(commentId);
	}

}
