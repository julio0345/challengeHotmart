package com.hotmart.challenge.domain.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hotmart.challenge.api.models.ProductInputDTO;
import com.hotmart.challenge.api.models.ProductOutputDTO;
import com.hotmart.challenge.domain.exceptions.NotFoundException;
import com.hotmart.challenge.domain.models.Category;
import com.hotmart.challenge.domain.models.Product;
import com.hotmart.challenge.domain.repositories.CategoryRepository;
import com.hotmart.challenge.domain.repositories.ProductRepository;
import com.hotmart.challenge.domain.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<ProductOutputDTO> findAll(Pageable pageable) {
		Page<Product> pageProduct = repository.findAll(pageable);
		List<ProductOutputDTO> list = pageProduct.stream().map(iteration -> iteration.toOutputModel())
				.collect(Collectors.toList());
		return new PageImpl(list);
	}

	public ProductOutputDTO post(ProductInputDTO productInput) {
		Category category = getCategoryById(productInput.getCategory().getId());
		
		Product product = toModel(productInput);
		product.setCategory(category);
		product.setCreationDate(OffsetDateTime.now());
		
		product = repository.save(product);
		return product.toOutputModel();
	}

	public void delete(Product product) {
		repository.delete(product);
	}

	public void deleteById(Long id) {
		repository.delete(getProductById(id));
	}

	public ProductOutputDTO update(Long id, ProductInputDTO productInput) {
		Category category = getCategoryById(productInput.getCategory().getId());
		
		Product product = getProductById(id);
		product.setName(productInput.getName());
		product.setDescription(productInput.getDescription());		
		product.setCategory(category);
		
		product = repository.save(product);
		return product.toOutputModel();
	}

	public ProductOutputDTO getProductOutputById(Long id) {
		return getProductById(id).toOutputModel();
	}

	private Product getProductById(Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found."));
		return product;
	}

	private Product toModel(ProductInputDTO product) {
		return mapper.map(product, Product.class);
	}

	private Category getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Category not found"));
		return category;
	}
}