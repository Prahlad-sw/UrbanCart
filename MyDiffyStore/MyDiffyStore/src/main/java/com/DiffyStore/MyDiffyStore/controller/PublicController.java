package com.DiffyStore.MyDiffyStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DiffyStore.MyDiffyStore.models.Product;
import com.DiffyStore.MyDiffyStore.repository.ProductRepo;
import com.DiffyStore.MyDiffyStore.repository.UserInfoRepository;

//@RestController
//@RequestMapping("/api/public")
//public class PublicController {
//	@Autowired
//	ProductRepo productRepo;
//	
//	@Autowired
//	UserInfoRepository userRepo;
//	
//	
//	@GetMapping("/product/search")
//	public ResponseEntity<List<Product>> getProducts(@RequestParam String keyword){
//	try {	
//		if(keyword.isEmpty()) {
//			return ResponseEntity.status(400).build();
//		}
//			
//            List<Product> products = productRepo
//                .findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);
//            if (products.isEmpty()) {
//                return ResponseEntity.status(404).body(products); // Return 404 if no products are found
//            }
//            return ResponseEntity.ok(products);
//        } 
//	catch (Exception e) {
//		e.printStackTrace();
//            return ResponseEntity.status(500).build(); // or log the exception and return meaningful error
//        }
//	}
//
//}

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> getProducts(@RequestParam String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Product> products = productRepo
                .findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);

            // Return 200 OK with empty list if no products found
            return ResponseEntity.ok(products);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
