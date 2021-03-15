package com.hotmart.challenge.api.models;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInputDTO {

	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	private CategoryInputDTO category;
}