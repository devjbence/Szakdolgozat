package com.szakdoga.entities.DTOs;

import java.util.List;

import com.szakdoga.enums.ProductType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {
	private List<ProductFilterCore> productFilterCores;
	private List<Integer> categories;
	private String productName;
	private ProductType productType;
	private Boolean isActive;
	private Boolean own;
}
