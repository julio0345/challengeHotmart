package com.hotmart.challenge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.hotmart.challenge.api.models.CategoryInputDTO;
import com.hotmart.challenge.api.models.ProductInputDTO;
import com.hotmart.challenge.api.models.ProductOutputDTO;
import com.hotmart.challenge.domain.models.Category;
import com.hotmart.challenge.domain.models.Product;
import com.hotmart.challenge.domain.repositories.CategoryRepository;
import com.hotmart.challenge.domain.repositories.ProductRepository;
import com.hotmart.challenge.domain.services.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	
	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	ProductRepository productRepository;

	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	CategoryRepository categoryRepository;
	
	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	ModelMapper modelMapper;
	
	@InjectMocks
	ProductService productService;

	Product product = new Product();
	Category category = new Category();
	ProductInputDTO productInputModel = new ProductInputDTO();
	
	  @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
			category.setId(1L);
			category.setName("Tecnology");

			product.setCategory(category);
			product.setCreationDate(OffsetDateTime.now());
			product.setDescription("ESR - Especialist Spring Rest Course to initializing");
			product.setId(1L);
			product.setName("ESR - Especialist Spring Rest");
			CategoryInputDTO categoryInputModel = new CategoryInputDTO();
			categoryInputModel.setId(1L);
			
			productInputModel.setCategory(categoryInputModel);
			productInputModel.setDescription("Tecnology");
			productInputModel.setName("ESR - Especialist Spring Rest");
	  }
	  
	@Test
	public void saveProduct() {
		 when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		 
		 when(productRepository.save(product)).thenReturn(product);
		 
		 when(modelMapper.map(productInputModel, Product.class)).thenReturn(product);
		 
		 ProductOutputDTO productOutput = productService.post(productInputModel);
		 
		 assertEquals("ESR - Especialist Spring Rest", productOutput.getName());
	}
}