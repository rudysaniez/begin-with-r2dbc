package com.me.r2dbc.h2.review.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.me.r2dbc.h2.review.Api;
import com.me.r2dbc.h2.review.Application.PaginationInformation;
import com.me.r2dbc.h2.review.bo.ReviewEntity;
import com.me.r2dbc.h2.review.exception.InvalidInputException;
import com.me.r2dbc.h2.review.exception.NotFoundException;
import com.me.r2dbc.h2.review.mapper.ReviewMapper;
import com.me.r2dbc.h2.review.model.PageMetadata;
import com.me.r2dbc.h2.review.model.PagedReview;
import com.me.r2dbc.h2.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ReviewController {

	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);
	private final PaginationInformation paginationInformation;
	
	@Autowired
	public ReviewController(ReviewRepository reviewRepository, PaginationInformation paginationInformation) {
		
		this.reviewRepository = reviewRepository;
		this.paginationInformation = paginationInformation;
	}
	
	/**
	 * @param id
	 * @return {@link ReviewEntity}
	 */
	@GetMapping(value = Api.REVIEW_PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<ReviewEntity>> getByReviewID(@PathVariable(required = true, name="id") Integer id) {
		
		if(id < 1) throw new InvalidInputException("The id must be greater than 0");
		
		log.info(" > Search a review by reviewID=%d", id);
		
		return reviewRepository.findByReviewID(id).
				switchIfEmpty(Mono.error(new NotFoundException(String.format("Reviews with productID=%d doesn't not exists.", id)))).
				map(r -> ResponseEntity.ok(r));
	}
	
	/**
	 * @param productId
	 * @param page
	 * @param size
	 * @return {@link PagedReview}
	 */
	@GetMapping(value = Api.REVIEW_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<PagedReview>> getByProductID(@RequestParam(name = "productId", required = false) Integer productId, 
			@RequestParam(name = "page", required = false) Integer page, 
			@RequestParam(name = "size", required = false) Integer size) {
		
		if(page == null || page < 0) page = paginationInformation.getDefaultPage();
		if(size == null || size < 1) size = paginationInformation.getDefaultSize();
		
		final Integer pageNumber = page;
		final Integer pageSize = size;
		
		if(productId != null) {
			
			return reviewRepository.countByProductID(productId).
				log().
				transform(m -> m.flatMap(count -> reviewRepository.findByProductID(productId, PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "reviewID"))).
						map(reviewMapper::toModel).collectList().
						map(list -> PagedReview.builder().content(list).
								page(PageMetadata.builder().
										number(Integer.toUnsignedLong(pageNumber)).
										size(Integer.toUnsignedLong(pageSize)).
										totalElements(count).
										totalPages(count < pageSize ? 1L : count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1) 
										.build()).build())
						).
						map(pagedReview -> ResponseEntity.ok(pagedReview))
				).log();
		}
		else {
			
			return reviewRepository.count().
				log().
				transform(m -> m.flatMap(count -> reviewRepository.findByProductIDNotNull(PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "reviewID"))).
					map(reviewMapper::toModel).collectList().
					map(list -> PagedReview.builder().content(list).
							page(PageMetadata.builder().
									number(Integer.toUnsignedLong(pageNumber)).
									size(Integer.toUnsignedLong(pageSize)).
									totalElements(count).
									totalPages(count < pageSize ? 1L : count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1) 
									.build()).build())
					).
					map(pagedReview -> ResponseEntity.ok(pagedReview))
				).log();
		}
	}
	
	/**
	 * @param productId
	 * @return {@link Void}
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping(value = Api.REVIEW_PATH + "/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "id", required = true) Integer productId) {
		
		if(productId < 1) throw new InvalidInputException("The productId must be greater than 0");
		
		log.info(" > Delete reviews by productID=%d", productId);
		
		return reviewRepository.deleteByProductID(productId).map(v -> ResponseEntity.ok(v));
	}
}
