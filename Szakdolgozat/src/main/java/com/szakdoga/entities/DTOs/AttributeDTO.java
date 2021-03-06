package com.szakdoga.entities.DTOs;
import lombok.Data;

@Data
public class AttributeDTO {
	private Integer id;
	private Integer attributeCore;
	private String value;
	private Integer product;
	
	@Override
	public String toString() {
		return "AttributeDTO [id=" + id + ", attributeCore=" + attributeCore + ", value=" + value + ", product="
				+ product + "]";
	}
}
