package com.szakdoga.entities.DTOs;

import com.szakdoga.enums.AttributeType;

public class AttributeDTO {
	private String name;
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
