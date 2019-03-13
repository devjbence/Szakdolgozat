package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.RefreshTokenEntity;

import java.lang.String;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String>{
	RefreshTokenEntity findByTokenId(String tokenid);
}
