package com.DiffyStore.MyDiffyStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DiffyStore.MyDiffyStore.models.CartProduct;

import jakarta.transaction.Transactional;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct,Integer>{
	Optional<CartProduct> findByCartUserUserIdAndProductProductId(Integer userId,Integer ProductId);
	
	@Transactional 
	void deleteByCartUserUserIdAndProductProductId(Integer userId,Integer productId);
	
}
