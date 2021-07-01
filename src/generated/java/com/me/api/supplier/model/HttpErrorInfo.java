package com.me.api.supplier.model;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HttpErrorInfo {

	@JsonProperty(value = "timestamp", required = true)
	private ZonedDateTime timestamp;
	
	@NotEmpty
	@JsonProperty(value = "path", required = true)
    private String path;
	
	@JsonProperty(value = "httpStatus", required = true)
    private Integer httpStatus;
	
	@NotEmpty
	@JsonProperty(value = "message", required = true)
    private String message;
}
