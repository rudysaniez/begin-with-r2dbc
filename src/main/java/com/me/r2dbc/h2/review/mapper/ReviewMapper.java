package com.me.r2dbc.h2.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.me.r2dbc.h2.review.bo.ReviewEntity;
import com.me.r2dbc.h2.review.model.Review;

@Mapper
public interface ReviewMapper {

	/**
	 * @param review
	 * @return {@link ReviewEntity}
	 */
	@Mappings(value = {
		@Mapping(target = "id", ignore = true), 
		@Mapping(target = "withId", ignore = true)
	})
	public ReviewEntity toEntity(Review review);
	
	/**
	 * @param review
	 * @return {@link Review}
	 */
	public Review toModel(ReviewEntity review);
}
