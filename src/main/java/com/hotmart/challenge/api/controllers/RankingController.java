package com.hotmart.challenge.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotmart.challenge.api.models.RankingOutputDTO;
import com.hotmart.challenge.domain.services.RankingService;

@RestController
@RequestMapping("/ranking")
public class RankingController {

	@Autowired
	private RankingService rankingService;

	@GetMapping("/{category}")
	public ResponseEntity<RankingOutputDTO> verifyRanking(@PathVariable String category) {
		RankingOutputDTO ranking = rankingService.listRanking(category);
		return ResponseEntity.ok(ranking);
	}
}