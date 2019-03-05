package com.szakdoga.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.szakdoga.enums.AttributeType;

@Entity
public class Attribute extends EntityBase{
	
	private String name;
	@Enumerated(EnumType.ORDINAL)
	private AttributeType type;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AttributeType getType() {
		return type;
	}
	public void setType(AttributeType type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
