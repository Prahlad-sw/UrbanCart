package com.DiffyStore.MyDiffyStore.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DiffyStore.MyDiffyStore.models.Category;
import com.DiffyStore.MyDiffyStore.models.Product;
import com.DiffyStore.MyDiffyStore.models.UserInfo;
import com.DiffyStore.MyDiffyStore.repository.CategoryRepo;
import com.DiffyStore.MyDiffyStore.repository.ProductRepo;
import com.DiffyStore.MyDiffyStore.repository.UserInfoRepository;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired 
	UserInfoRepository userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	
	// Having error, seller object turning to null Rest is solved, removed JsonIgnore from categoryId, so it's not setting to 0 by default
	//verified , create a new product as JPA has some issue
	@PostMapping("/product")
	public ResponseEntity<Object> postProduct(Principal principal,@RequestBody Product product){
		
		String username=principal.getName();
		UserInfo user =userRepo.findByUsername(username).orElse(null);

		if(user==null) {
			return ResponseEntity.badRequest().body("user not found");
		}
		
		Integer catId = (product.getCategory() != null) ? product.getCategory().getCategoryId() : null;
		if (catId == null || catId==0) {
		      return ResponseEntity.badRequest().body("Category ID is required");
		}
		    // Fetch the actual Category entity
		 Optional <Category> categoryOpt = categoryRepo.findById(catId);
	    if (categoryOpt.isEmpty()) {
	        return ResponseEntity.badRequest().body("Category not found");
	    }

	    Category category = categoryOpt.get();
		
		//setting new product
		Product newProduct = new Product();
		newProduct.setProductName(product.getProductName());
		newProduct.setPrice(product.getPrice());
		newProduct.setCategory(category);
		newProduct.setSeller(user);
		
		Product product1=productRepo.save(newProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body("http://localhost:8090/api/auth/seller/product/"+product1.getProductId());
	}
	
	//Verified
	@GetMapping("/product")
	public ResponseEntity<Object> getCaetAllPProduct(Principal principal){
		String username=principal.getName();
		UserInfo user =userRepo.findByUsername(username).orElse(null);
		if(user==null) {
			return ResponseEntity.badRequest().body("user not found");
		}
		List<Product> list=productRepo.findBySellerUserId(user.getUserId());
		return ResponseEntity.ok(list);
	}
	
	//verified
	@GetMapping("/product/{productId}")
	public ResponseEntity<Object> getProduct(Principal principal,@PathVariable Integer productId){
		String username=principal.getName();
		UserInfo user =userRepo.findByUsername(username).orElse(null);
		
		if(user==null) {
			return ResponseEntity.badRequest().body("user not found");
		}
		
		//System.out.println(user.getUsername()+"This username");
		Product prod=productRepo.findBySellerUserIdAndProductId(user.getUserId(), productId).orElse(null);
		//System.out.println(prod);
		if(prod==null) {
			return ResponseEntity.status(404).body("product not found in GET Mapping ID in SELLER");
		}
		return ResponseEntity.ok(prod);
	}
	
	@PutMapping("/product")
	public ResponseEntity<Object> updateCart(Principal principal , @RequestBody Product updateProduct){
		String username=principal.getName();

		UserInfo user =userRepo.findByUsername(username).orElse(null);

		if(user==null) {
			return ResponseEntity.badRequest().body("user not found");
		}
		
		
		Product prod=productRepo.findBySellerUserIdAndProductId(user.getUserId(), updateProduct.getProductId()).orElse(null);
		Category category=categoryRepo.findByCategoryName(updateProduct.getCategory().getCategoryName()).get();
		
		prod.setProductName(updateProduct.getProductName());
		prod.setPrice(updateProduct.getPrice());
		prod.setCategory(category);
		prod.setSeller(user);
		Product prod1=productRepo.save(prod);
		return ResponseEntity.ok(prod1);
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Object> deleteCart(Principal principal,@PathVariable Integer productId){
		String username=principal.getName();
		System.out.println("Checkpost 1 ---------------");
		UserInfo user =userRepo.findByUsername(username).orElse(null);
		if(user==null) {
			return ResponseEntity.badRequest().body("user not found");
		}
		System.out.println("Checkpost 2 ---------------");

		Product product=productRepo.findBySellerUserIdAndProductId(user.getUserId(), productId).orElse(null);
		if(product==null) {
			return ResponseEntity.status(404).body("delete in seller error");
		}
		System.out.println("Checkpost 3 ---------------");

		productRepo.delete(product);
		System.out.println("Checkpost 4 ---------------");

		return ResponseEntity.ok(product);
		
	}

}
