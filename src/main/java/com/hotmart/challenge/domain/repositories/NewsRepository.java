package com.hotmart.challenge.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.models.Category;
import com.hotmart.challenge.domain.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>{
	List<News> findByCategory(Category category);
}