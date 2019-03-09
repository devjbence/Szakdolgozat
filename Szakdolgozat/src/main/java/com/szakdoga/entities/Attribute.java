package com.szakdoga.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.szakdoga.enums.AttributeType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Attribute extends EntityBase{
	@ManyToOne
	@JoinColumn(name = "attribute_name_id")
	private AttributeName attributeName;
	@Enumerated(EnumType.ORDINAL)
	private AttributeType type;
	private String value;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
