package com.hotmart.challenge.domain.services.interfaces;

import java.util.List;

import com.hotmart.challenge.domain.models.Product;

public interface IRankingService {
	
	List<Product> calculateProductsAveragesRanking(String categoryDescription);
	void updateNewsCategory();
}