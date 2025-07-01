package com.DiffyStore.MyDiffyStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DiffyStore.MyDiffyStore.service.JwtService;

@RestController
@RequestMapping("/napi")
public class DemoController {
	
	@Autowired
	public JwtService jwtService;
	
	@GetMapping("/hello")
	public ResponseEntity getMethodName() {
        String say="Hello";
        return ResponseEntity.ok(say);
	}

}
