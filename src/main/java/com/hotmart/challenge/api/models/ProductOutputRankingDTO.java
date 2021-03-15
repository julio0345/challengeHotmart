package com.hotmart.challenge.api.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(Include.NON_NULL)
public class ProductOutputRankingDTO {

	private Long id;
	private String name;
	private String description;	
	private OffsetDateTime creationDate;
	private BigDecimal score;
}