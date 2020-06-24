package com.codesquad.issue04.utils;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

// <<<<<<< HEAD
// 	@CreatedDate
// 	private LocalDateTime createdDate;
//
// 	@LastModifiedDate
// 	private LocalDateTime modifiedDate;
// =======
// 	// @Column(name = "created_by")
// 	// @CreatedBy
// 	// private String createdBy;
// 	//
// 	// @Column(name = "updated_by")
// 	// @LastModifiedBy
// 	// private String updatedBy;
// 	//
// 	// @Column(name = "created_on")
// 	// @CreatedDate
// 	// private LocalDateTime createdDate;
// 	//
// 	// @Column(name = "updated_on")
// 	// @LastModifiedDate
// 	// private LocalDateTime modifiedDate;
//
// >>>>>>> BE/feat/#91
}
