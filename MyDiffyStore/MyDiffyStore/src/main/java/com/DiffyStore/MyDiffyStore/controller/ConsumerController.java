package com.DiffyStore.MyDiffyStore.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DiffyStore.MyDiffyStore.models.Cart;
import com.DiffyStore.MyDiffyStore.models.CartProduct;
import com.DiffyStore.MyDiffyStore.models.Product;
import com.DiffyStore.MyDiffyStore.models.UserInfo;
import com.DiffyStore.MyDiffyStore.repository.CartProductRepo;
import com.DiffyStore.MyDiffyStore.repository.CartRepo;
import com.DiffyStore.MyDiffyStore.repository.ProductRepo;
import com.DiffyStore.MyDiffyStore.repository.UserInfoRepository;
import com.DiffyStore.MyDiffyStore.service.JwtService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CartProductRepo cpRepo;
	
	@Autowired 
	UserInfoRepository userRepo;
	
	@Autowired
	JwtService jwtService;
	
	//verified
	@GetMapping("/cart")
	public ResponseEntity<Object> getCart(Principal principal,@RequestHeader("Authorization") String AuthHeader){
		String username=principal.getName();
		Cart cart=cartRepo.findByUserUsername(username).orElse(null);
		return ResponseEntity.status(200).body(cart);
	}
	
	//Verified
	@PostMapping("/cart")
	public ResponseEntity<Object> postCart(Principal principal,@RequestBody Product product){
		
		    String username = principal.getName();
		    Cart cart=cartRepo.findByUserUsername(username).orElse(null);
		    Product prod=productRepo.findById(product.getProductId()).orElse(null);
		    
		    if(cart.getCartProducts().size() > 0 && !cart.getCartProducts().stream().anyMatch(n->n.getProduct().equals(prod))) {
		    	CartProduct cp=new CartProduct();
		    	cp.setCart(cart);
		    	if(prod!=null)
		    		prod.setProductName(product.getProductName());
		    	cp.setProduct(prod);
		    	cp.setQuantity(1);
		    	cart.getCartProducts().add(cp);
		    	cart.updateTotalAmount(prod.getPrice());
		    	cpRepo.save(cp);
		    	cartRepo.save(cart);
		    	return ResponseEntity.ok(cart);
		    }
		    else {
		    	return ResponseEntity.status(409).build();
		    }
	}
	
	//Verified
	@PutMapping("/cart")
	public ResponseEntity<Object> updateCart(Principal principal,@RequestBody CartProduct cartProd){
		String username = principal.getName();
		
		UserInfo user=userRepo.findByUsername(username).orElse(null);
		Cart cart=cartRepo.findByUserUsername(username).orElse(null);
		if(user==null) {
			return ResponseEntity.badRequest().build();
		}
		Product prod=productRepo.findById(cartProd.getProduct().getProductId()).orElse(null);
		if(prod==null) {
			return ResponseEntity.badRequest().build();
		}
		CartProduct  cp=cart
				.getCartProducts().stream().filter(n->n.getProduct().equals(prod)).findFirst().orElse(null);
		if(cp==null){
			if(cartProd.getQuantity()>0) {
				cp = new CartProduct();
			    cp.setProduct(prod);
			    cp.setQuantity(cartProd.getQuantity());
			    //cp.setCart(cart);
			    cart.updateTotalAmount(prod.getPrice() * cartProd.getQuantity());
				cp.setCart(cart);
				cart.getCartProducts().add(cp);
				cpRepo.save(cp);
			}
		}else {
		    if (cartProd.getQuantity() == 0) {
		        cart.getCartProducts().remove(cp);
		        cart.updateTotalAmount(-prod.getPrice() * cp.getQuantity());
		        cpRepo.delete(cp);
		    } else {
		        cart.updateTotalAmount(prod.getPrice() * (cartProd.getQuantity() - cp.getQuantity()));
		        cp.setQuantity(cartProd.getQuantity()); // update the quantity
		        cpRepo.save(cp);
		    }
		}
		
		cartRepo.saveAndFlush(cart);
		//return ResponseEntity.status(200).build();
		return ResponseEntity.ok(cart);
	
	}
	
	//Verified
	@DeleteMapping("/cart")
	public ResponseEntity<Object> deleteCart(Principal principal,@RequestBody Product prod){
		String username = principal.getName();
		Cart cart=cartRepo.findByUserUsername(username).orElse(null);
		if(cart==null) {
			return ResponseEntity.badRequest().build();
		}
		CartProduct cp=cart.getCartProducts().stream()
				.filter(n->n.getProduct().getProductId()==prod.getProductId()).findFirst().orElse(null);
		
		cart.getCartProducts().remove(cp);
		cart.updateTotalAmount(-cp.getProduct().getPrice()*cp.getQuantity());
		cpRepo.delete(cp);
		cartRepo.save(cart);
		return ResponseEntity.ok("Deleted");//return only 200
	}
}
