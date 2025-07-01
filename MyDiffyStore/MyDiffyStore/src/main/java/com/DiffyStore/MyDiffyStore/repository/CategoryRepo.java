package com.DiffyStore.MyDiffyStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DiffyStore.MyDiffyStore.models.Category;

@Repository
public interface CategoryRepo  extends JpaRepository<Category,Integer>{
	Optional<Category> findByCategoryName(String category);

}
