package com.szakdoga.entities.DTOs;

import com.szakdoga.enums.AttributeType;
import lombok.Data;

@Data
public class AttributeDTO {
	private int id;
	private String name;
	private AttributeType type;
	private String value;
}
