package com.hotmart.challenge.api.models;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutputDTO {
	
	private Long id;
	private String name;
	private String description;
	private OffsetDateTime creationDate;
	private CategoryOutputDTO category;
	private Double score;
}