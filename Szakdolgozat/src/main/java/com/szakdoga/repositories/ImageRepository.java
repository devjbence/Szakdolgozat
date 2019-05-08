package com.szakdoga.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	Optional<Image> findById(Integer id);
}
