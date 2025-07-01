package com.DiffyStore.MyDiffyStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DiffyStore.MyDiffyStore.models.Cart;

@Repository
public interface CartRepo  extends JpaRepository<Cart,Integer>{
	Optional<Cart> findByUserUsername(String username);
}
