package com.hotmart.challenge.domain.repositories;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.models.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
	
	List<Sale> findBySaleDateGreaterThan(OffsetDateTime saleDate);
	
	@Query("select s from Sale s join  s.product p join  p.category c where s.saleDate >= ?1 and c.name = ?2")
	List<Sale> findSaleLastYearByCategory(OffsetDateTime saleDate, String categoryDescription);

}