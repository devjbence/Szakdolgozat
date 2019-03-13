package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.AccessTokenEntity;

import java.lang.String;
import java.util.List;

public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String>{
	AccessTokenEntity findByUsername(String username);
}
