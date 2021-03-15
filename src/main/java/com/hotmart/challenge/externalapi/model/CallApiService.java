package com.hotmart.challenge.externalapi.model;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class CallApiService {

	public ApiNews callApiNews(String url, Map<String, Object> params) {
		String parameters = getParameters(params);		
		RestTemplate restTemplate = new RestTemplate();
		
		ApiNews apiNews = restTemplate.getForObject(url + parameters, ApiNews.class);
		return apiNews;
	}

	private String getParameters(Map<String, Object> params) {
		String parameters = "?";
		
		for (Map.Entry<String, Object> parameter : params.entrySet()) {
			parameters += parameter.getKey() + "=" + parameter.getValue() + "&";
		}
		return parameters.substring(0, parameters.length() -1);
	}
}