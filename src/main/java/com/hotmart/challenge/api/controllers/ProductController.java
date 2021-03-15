package com.hotmart.challenge.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotmart.challenge.api.models.ProductInputDTO;
import com.hotmart.challenge.api.models.ProductOutputDTO;
import com.hotmart.challenge.domain.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductOutputDTO post(@Valid @RequestBody ProductInputDTO productInput) {
		return service.post(productInput);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductOutputDTO> find(@PathVariable Long id){
		ProductOutputDTO productOutput = service.getProductOutputById(id);
		return ResponseEntity.ok(productOutput);
	}

	
	@GetMapping
	public ResponseEntity<Page<ProductOutputDTO>> list(Pageable pageable){
		Page<ProductOutputDTO> pageProducts = service.findAll(pageable); 
		return ResponseEntity.ok(pageProducts);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductOutputDTO> update(@PathVariable Long id, @RequestBody ProductInputDTO productInput){
		ProductOutputDTO productOutput = service.update(id, productInput);
		return ResponseEntity.ok(productOutput);
	}
}