package com.hotmart.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.hotmart.challenge.domain.repositories.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductIntegrationTest {
	final String BASE_PATH = "http://localhost:8080/products";
	
	private RestTemplate restTemplate;
	
	private ObjectMapper mapper;
	
	@Autowired
	private CategoryRepository  categoryRepository;

	@Before
	public void init() {
		restTemplate = new RestTemplate();
		mapper = new ObjectMapper();
		
		Category category = new Category();
		category.setName("Category UniK");
		categoryRepository.save(category);
	}
	
	@Test
	public void testPost() throws JsonProcessingException {
		ProductInputDTO product = new ProductInputDTO();
		CategoryInputDTO categoryInputModel = new CategoryInputDTO();
		categoryInputModel.setId(1L);

		product.setCategory(categoryInputModel);
		product.setDescription("Tecnology");
		product.setName("ESR - Especialist Spring Rest");
		
		ProductOutputDTO response = restTemplate.postForObject(BASE_PATH, product, ProductOutputDTO.class);
		assertEquals("Tecnology", response.getDescription());
	}
	
	@Test
	public void testGet() throws JsonProcessingException {
		String response = restTemplate.getForObject(BASE_PATH + "/1", String.class);
        List<ProductOutputDTO> productOutput = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, ProductOutputDTO.class));
    	
        assertEquals("Tecnology", productOutput.get(0).getDescription());
    	assertEquals("ESR - Especialist Spring Rest", productOutput.get(0).getName());
    	assertEquals(1, productOutput.get(0).getCategory().getId());
	}
	
	@Test
	public void testPut() throws JsonProcessingException {
//		ProductInputModel product = new ProductInputModel();
//		CategoryInputModel categoryInputModel = new CategoryInputModel();
//		categoryInputModel.setId(1L);
//
//		product.setCategory(categoryInputModel);
//		product.setDescription("Tecnology Updated");
//		product.setName("ITA - COURSES");
//		
//		ProductOutputModel response = restTemplate.put(BASE_PATH, product, ProductOutputModel.class);
//		assertEquals("Tecnology", response.getDescription());
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", "1");
	     
	    ProductInputDTO product = new ProductInputDTO("TEcnology Updated", "ITA_ COURSES", new CategoryInputDTO(1L));
	     
	    restTemplate.put(BASE_PATH, product, params);
	}
	
	@Test
	public void buscaUmContatoDeveRetornarNaoEncontrado() {

//		ResponseEntity<Contato> resposta =
//				testRestTemplate.exchange("/agenda/contato/{id}",HttpMethod.GET,null, Contato.class,100 );
//
//		Assert.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
//		Assert.assertNull(resposta.getBody());
	}

	
	@After
	public void end() {
		categoryRepository.deleteAll();
	}
}