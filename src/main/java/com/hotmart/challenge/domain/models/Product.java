package com.hotmart.challenge.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.hotmart.challenge.api.models.ProductOutputDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_category")
	private Category category;
	
	private String description;
	
	@Column(name = "creation_date")
	private OffsetDateTime creationDate;
	
	@Transient
	private BigDecimal averageEvaluation;
	
	@Transient
	private BigDecimal averageSaleCreationDate;
	
	public ProductOutputDTO toOutputModel() {
		return new ProductOutputDTO(getId(), getName(), getDescription(), getCreationDate(), getCategory().toOutputModel(), null);
	}
}