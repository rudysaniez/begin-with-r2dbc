package com.me.r2dbc.h2.review.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded.Nullable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data @Builder
@Table(value = "review")
public class Review implements Serializable {

	public static String insert;
	public static String count;
	public static String deleteAll;
	public static String findByReviewID;
	
	static {
		
		insert = "insert into review (reviewID, productID, author, subject, content, creationDate) values " +
				 "(:reviewID, :productID, :author, :subject, :content, :creationDate)";
		
		count = "select count(*) from review";
		
		deleteAll = "delete from review";
		
		findByReviewID = "select id, reviewID, productID, author, subject, content, creationDate, updateDate "
				+ "from review where reviewID=:reviewID";
	}
	
	@ToStringExclude @Exclude
	@Id @Column("id")
	private Integer id;
	
	@NotNull
	@Column("reviewID")
	private Integer reviewID;
	
	@NotNull
	@Column("productID")
	private Integer productID;
	
	@NotEmpty @Exclude
	@Column("author")
	private String author;
	
	@NotEmpty @Exclude
	@Column("subject")
	private String subject;
	
	@NotEmpty @Exclude
	@Column("content")
	private String content;
	
	@Exclude @Nullable
	@Column("creationDate")
	private LocalDateTime creationDate;
	
	@Exclude @Nullable
	@Column("updateDate")
	private LocalDateTime updateDate;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param reviewID
	 * @param productID
	 * @param author
	 * @param subject
	 * @param content
	 */
	public Review(Integer reviewID, Integer productID, String author, String subject, String content) {
		
		this.reviewID = reviewID;
		this.productID = productID;
		this.author = author;
		this.subject = subject;
		this.content = content;
		this.creationDate = LocalDateTime.now();
	}
}
