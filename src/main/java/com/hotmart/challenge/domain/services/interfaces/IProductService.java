package com.hotmart.challenge.domain.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotmart.challenge.api.models.ProductOutputDTO;

public interface IProductService {

	Page<ProductOutputDTO> findAll(Pageable pageable);
}