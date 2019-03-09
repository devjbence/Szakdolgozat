package com.szakdoga.entities.DTOs;

import com.szakdoga.enums.AttributeType;
import lombok.Data;

@Data
public class AttributeDTO {
	private Integer id;
	private Integer attributeName;
	private AttributeType type;
	private String value;
	private Integer product;
}
