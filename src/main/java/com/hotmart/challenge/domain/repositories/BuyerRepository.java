package com.hotmart.challenge.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.models.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long>{

}