package com.szakdoga.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Attribute extends EntityBase{
	@ManyToOne
	@JoinColumn(name = "attribute_name_id")
	private AttributeCore attributeCore;
	private String value;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	public Integer getIntValue() {
		return Integer.parseInt(value);
	}

	public Double getDoubleValue() {
		return Double.parseDouble(value);
	}
}
