package com.szakdoga.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterCore {
	private Integer attributeCore;
	private Integer attributeOperation;
	private String value;
	
	public Integer getIntValue() {
		return Integer.parseInt(value);
	}

	public Double getDoubleValue() {
		return Double.parseDouble(value);
	}
}
