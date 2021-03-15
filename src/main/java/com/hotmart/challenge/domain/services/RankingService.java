package com.hotmart.challenge.domain.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotmart.challenge.api.models.ProductOutputRankingDTO;
import com.hotmart.challenge.api.models.ProductSaleDTO;
import com.hotmart.challenge.api.models.RankingOutputDTO;
import com.hotmart.challenge.domain.models.Category;
import com.hotmart.challenge.domain.models.News;
import com.hotmart.challenge.domain.models.Product;
import com.hotmart.challenge.domain.models.Sale;
import com.hotmart.challenge.domain.repositories.CategoryRepository;
import com.hotmart.challenge.domain.repositories.NewsRepository;
import com.hotmart.challenge.domain.repositories.SaleRepository;
import com.hotmart.challenge.domain.services.interfaces.IRankingService;
import com.hotmart.challenge.externalapi.model.ApiNews;
import com.hotmart.challenge.externalapi.model.CallApiService;

@Service
public class RankingService implements IRankingService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private CallApiService callApiService;

	@Autowired
	private CategoryRepository categoryRepository;

	private Map<Long, ProductSaleDTO> calculateAverageRating(String categoryDescription) {
		Map<Long, ProductSaleDTO> mapProductBySale = new HashMap<Long, ProductSaleDTO>();
		List<Sale> listSale = saleRepository.findSaleLastYearByCategory(
				OffsetDateTime.now().minusYears(1).truncatedTo(ChronoUnit.DAYS), categoryDescription);
		Long key = 0L;

		for (Sale item : listSale) {
			if (null != item.getEvaluation()) {
				key = item.getProduct().getId();

				if (mapProductBySale.containsKey(key)) {
					mapProductBySale.get(key).setTotalEvaluation(
							mapProductBySale.get(key).getTotalEvaluation() + item.getEvaluation().longValue());
					mapProductBySale.get(key)
							.setSaleWithEvaluation(mapProductBySale.get(key).getSaleWithEvaluation() + 1);
				} else {
					mapProductBySale.put(key,
							new ProductSaleDTO(item.getProduct(), 1L, 0L, item.getEvaluation().longValue()));
				}
			} else {
				if (mapProductBySale.containsKey(key)) {
					mapProductBySale.get(key)
							.setSaleWithoutEvaluation(mapProductBySale.get(key).getSaleWithoutEvaluation() + 1);
				} else {
					mapProductBySale.put(key,
							new ProductSaleDTO(item.getProduct(), 0L, 1L, item.getEvaluation().longValue()));
				}
			}
		}
		return mapProductBySale;
	}

	/**
	 * This method should be executed time to update the variables X and Y from
	 * ranking formula where X = average of evaluation and Y = average of number of
	 * sale per day
	 */
	@Override
	public List<Product> calculateProductsAveragesRanking(String categoryDescription) {
		Map<Long, ProductSaleDTO> mapProductSale = calculateAverageRating(categoryDescription);
		List<Product> listProduct = new ArrayList<Product>();

		for (Map.Entry<Long, ProductSaleDTO> productToUpdate : mapProductSale.entrySet()) {
			productToUpdate.getValue().getProduct()
					.setAverageEvaluation(productToUpdate.getValue().getAverageEvaluation());
			productToUpdate.getValue().getProduct()
					.setAverageSaleCreationDate(productToUpdate.getValue().getAverageSaleCreation());

			listProduct.add(productToUpdate.getValue().getProduct());
		}
		return listProduct;
	}

	/**
	 * This method need to be executed 4 times per day according to Job
	 */
	@Override
	public void updateNewsCategory() {
		List<News> listNews = new ArrayList<News>();
		List<Category> listCategory = categoryRepository.findAll();

		Date today = new Date();
		if (listCategory.size() > 0) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();

			for (Category item : listCategory) {
				mapParameters.put("q", item.getName());
				mapParameters.put("pageSize", 0);// The content of News doesn't matter. Just the quantity
				mapParameters.put("apiKey", "cef8f96aec3a430c94fc00af34680ea2");

				ApiNews apiNews = callApiService.callApiNews("https://newsapi.org/v2/top-headlines", mapParameters);

				News newsCategory = new News();
				newsCategory.setCategory(item);
				newsCategory.setDate(today);
				newsCategory.setQuantity(new BigDecimal(apiNews.getTotalResults()));
				newsRepository.save(newsCategory);

				listNews.add(newsCategory);
			}
		}
	}

	public RankingOutputDTO listRanking(String categoryDescription) {
		RankingOutputDTO ranking = new RankingOutputDTO();

		List<News> listNews = findNews(categoryDescription);
		List<Product> listProduct = calculateProductsAveragesRanking(categoryDescription);

		List<ProductOutputRankingDTO> listProductsRanking = new ArrayList<ProductOutputRankingDTO>();

		for (Product product : listProduct) {
			ProductOutputRankingDTO productRanking = new ProductOutputRankingDTO();

			productRanking.setId(product.getId());
			productRanking.setName(product.getName());
			productRanking.setDescription(product.getDescription());
			productRanking.setCreationDate(product.getCreationDate());

			BigDecimal score = sumScore(product.getAverageEvaluation(), product.getAverageSaleCreationDate(),
					listNews.get(0).getQuantity());
			productRanking.setScore(score);

			listProductsRanking.add(productRanking);
		}

		listProductsRanking = listProductsRanking.stream()
				.sorted(Comparator.comparing(ProductOutputRankingDTO::getScore, Comparator.reverseOrder())
						.thenComparing(ProductOutputRankingDTO::getName))
				.collect(Collectors.toList());
	
		ranking.setListProducts(listProductsRanking);
		ranking.setSearchDate(OffsetDateTime.now());
		ranking.setSearchTerm(categoryDescription);
		return ranking;
	}

	/** 
	 * @param variableX - Average of Evaluation of each product
	 * @param variableY - Average of sale since creation date
	 * @param variableZ - Quantity of news of category
	 * @return the formula is the SUM of all values
	 */
	private BigDecimal sumScore(BigDecimal variableX, BigDecimal variableY, BigDecimal variableZ) {
		return variableX.add(variableY).add(variableZ);
	}

	private void executeJob() {
		try {
			newsRepository.deleteAll();
			updateNewsCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<News> findNews(String category) {
		List<News> listNews = new ArrayList<News>();

		boolean executeJob = needExecuteJOb();

		if (executeJob) {
			executeJob();
		}

		if (null != category) {
			List<Category> listCategory = categoryRepository.findByName(category);

			if (listCategory.size() > 0) {
				listNews = newsRepository.findByCategory(listCategory.get(0));
			}
		} else {
			listNews = newsRepository.findAll();
		}
		return listNews;
	}

	/***
	 * FAKE - TIME OUT :( Method to validate if is nedded to execute the Job to call
	 * the API News
	 * 
	 * @return true = need to call OR false = Don't need to call
	 */
	private boolean needExecuteJOb() {
		return false;
	}
}