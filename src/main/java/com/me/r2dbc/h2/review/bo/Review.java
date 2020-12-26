package com.me.r2dbc.h2.review.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded.Nullable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Table(value = "review")
public class Review implements Serializable {

	@Exclude
	@Id @Column("id")
	private final Integer id;
	
	@NotNull
	@Column("reviewID")
	private final Integer reviewID;
	
	@NotNull
	@Column("productID")
	private final Integer productID;
	
	@NotEmpty @Exclude
	@Column("author")
	private final String author;
	
	@NotEmpty @Exclude
	@Column("subject")
	private final String subject;
	
	@NotEmpty @Exclude
	@Column("content")
	private final String content;
	
	@Exclude @Nullable
	@Column("creationDate")
	private LocalDateTime creationDate;
	
	@Exclude @Nullable
	@Column("updateDate")
	private LocalDateTime updateDate;
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceConstructor
	public Review(Integer id, Integer reviewID, Integer productID, String author, String subject, String content) {
		
		this.id = id;
		this.reviewID = reviewID;
		this.productID = productID;
		this.author = author;
		this.subject = subject;
		this.content = content;
		this.creationDate = LocalDateTime.now();
	}
	
	/**
	 * @param id
	 * @return {@link Review}
	 */
	public Review withId(Integer id) {
		return new Review(id, reviewID, productID, author, subject, content);
	}
	
	
	/**
	 * Spring JDBC. 
	 */
	
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
}
