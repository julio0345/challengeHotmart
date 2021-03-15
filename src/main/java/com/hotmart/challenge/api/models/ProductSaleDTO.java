package com.hotmart.challenge.api.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import com.hotmart.challenge.domain.models.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDTO {

	private Product product;
	private Long saleWithEvaluation;
	private Long saleWithoutEvaluation;
	private Long totalEvaluation;

	public BigDecimal getAverageEvaluation() {
		return getTotalEvaluation() > 0 ? new BigDecimal(getTotalEvaluation())
				.divide(new BigDecimal(getSaleWithEvaluation()), 2, RoundingMode.HALF_EVEN) : BigDecimal.ZERO;
	}

	/***
	 * This method return the average of sale per day since creation date of product
	 * 
	 * @return average: Number of sale / number of days that product exists
	 */
	public BigDecimal getAverageSaleCreation() {
		long days = ChronoUnit.DAYS.between(getProduct().getCreationDate().toLocalDate(),
				OffsetDateTime.now().toLocalDate());
		return days <= 1 ? new BigDecimal(getTotalSale())
				: new BigDecimal(getTotalSale()).divide(new BigDecimal(days), 2, RoundingMode.HALF_EVEN);
	}

	public Long getTotalSale() {
		return saleWithEvaluation + saleWithoutEvaluation;
	}
}