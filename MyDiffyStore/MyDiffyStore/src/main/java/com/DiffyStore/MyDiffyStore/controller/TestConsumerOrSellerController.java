package com.DiffyStore.MyDiffyStore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/consumer")
public class TestConsumerOrSellerController {
	
	@GetMapping("/TestConsumer")
	public ResponseEntity<?> testConsumer(){
		String res="Testing Role Based Access on Consumer";
		return ResponseEntity.ok(res);
	}
}
