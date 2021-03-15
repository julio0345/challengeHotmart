package com.hotmart.challenge.externalapi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Api {

	public static void main(String[] args) {
//		RestTemplate restTemplate = new RestTemplate();
//		String fooResourceUrl
//		  = "https://newsapi.org/v2/top-headlines?q=apple&pageSize=0&apiKey=cef8f96aec3a430c94fc00af34680ea2";
//		
//		APIModel response = restTemplate.getForObject(fooResourceUrl, APIModel.class);
//		
		BigDecimal x = new BigDecimal(25L).divide(new BigDecimal(11L), 3, RoundingMode.HALF_UP);
		BigDecimal x2 = new BigDecimal(25L / 11L).setScale(3, RoundingMode.HALF_UP);
		BigDecimal y = new BigDecimal(11D);
		x.divide(y, 3, RoundingMode.HALF_UP);
		
		System.out.print(x);
	}
	
	public static class APIModel implements Serializable {
		private String status;
		private Long totalResults;
		
		public APIModel() {}
		
		public APIModel(String status, Long totalResults) {
			super();
			this.status = status;
			this.totalResults = totalResults;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Long getTotalResults() {
			return totalResults;
		}
		public void setTotalResults(Long totalResults) {
			this.totalResults = totalResults;
		}
	}
		
}