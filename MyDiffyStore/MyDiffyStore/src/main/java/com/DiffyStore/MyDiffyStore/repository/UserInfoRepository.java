package com.DiffyStore.MyDiffyStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DiffyStore.MyDiffyStore.models.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
	Optional<UserInfo> findByUsername(String username);
}
