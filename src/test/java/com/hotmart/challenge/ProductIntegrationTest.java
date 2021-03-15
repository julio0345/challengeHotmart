package com.hotmart.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotmart.challenge.api.models.CategoryInputDTO;
import com.hotmart.challenge.api.models.ProductInputDTO;
import com.hotmart.challenge.api.models.ProductOutputDTO;
import com.hotmart.challenge.domain.models.Category;
import com.hotmart.challenge.domain.models.Product;
import com.hotmart.challenge.domain.repositories.CategoryRepository;
import com.hotmart.challenge.domain.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductIntegrationTest {
	
	final String BASE_PATH = "http://localhost:8080/products";
	final String NAME_PRODUCT = "Tecnology Test_777";
	final String DESCRIPTION_PRODUCT = "ESR - Especialist Spring Rest";
	
	private RestTemplate restTemplate;
	
	private ObjectMapper mapper;
	
	private Category categoryTest;
	
	private Product productTest;
	
	@Autowired
	private ProductRepository  productRepository;
	
	@Autowired
	private CategoryRepository  categoryRepository;

	@Before
	public void init() {
		restTemplate = new RestTemplate();
		mapper = new ObjectMapper();
		
		Category category = new Category();
		category.setName("Category 777");
		categoryTest = categoryRepository.save(category);
		
		productTest = new Product();
		productTest.setCategory(categoryTest);
		productTest.setCreationDate(OffsetDateTime.now());
		productTest.setDescription(DESCRIPTION_PRODUCT);
		productTest.setName(NAME_PRODUCT);
		productTest = productRepository.save(productTest);
	}
	
	@Test
	public void test1PostProduct() throws JsonProcessingException {
		CategoryInputDTO categoryInputModel = new CategoryInputDTO();
		categoryInputModel.setId(categoryTest.getId());
		
		ProductInputDTO product = new ProductInputDTO();
		product.setCategory(categoryInputModel);
		product.setDescription(NAME_PRODUCT);
		product.setName(DESCRIPTION_PRODUCT);
		
		ProductOutputDTO response = restTemplate.postForObject(BASE_PATH, product, ProductOutputDTO.class);
		assertEquals(NAME_PRODUCT, response.getDescription());
	}
	
//	@Test
//	public void test2GetProduct() throws JsonProcessingException {
//		String response = restTemplate.getForObject(BASE_PATH + "/" + productTest.getId(), String.class);
//        List<ProductOutputDTO> productOutput = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, ProductOutputDTO.class));
//    	
//        assertEquals(DESCRIPTION_PRODUCT, productOutput.get(0).getDescription());
//    	assertEquals(NAME_PRODUCT, productOutput.get(0).getName());
//    	assertEquals(productTest.getId(), productOutput.get(0).getCategory().getId());
//	}
	
//	@Test
//	public void testDeleteProduct() {
//		  RestTemplate restTemplate = new RestTemplate();
//		    Map<String, Long> params = new HashMap<String, Long>();
//		    params.put("id", productTest.getId());
//		    restTemplate.delete ( BASE_PATH,  params );
//	}
	
//	@Test
//	public void test3PutProduct() throws JsonProcessingException {
//
//	}
	
	@After
	public void end() {

	}
}