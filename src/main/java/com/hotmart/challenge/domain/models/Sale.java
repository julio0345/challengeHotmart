package com.hotmart.challenge.domain.models;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seller")
	private Seller seller;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_buyer")
	private Buyer buyer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product")
	private Product product;

	@Column(name = "sale_date")
	private OffsetDateTime saleDate;

	private Integer evaluation;
}