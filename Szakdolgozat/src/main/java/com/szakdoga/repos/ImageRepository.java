package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	Image findById(Integer id);
}
