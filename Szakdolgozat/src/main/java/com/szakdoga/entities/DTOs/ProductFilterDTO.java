package com.szakdoga.entities.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {
	private List<ProductFilterCore> productFilterCores;
	private List<Integer> categories;
}
