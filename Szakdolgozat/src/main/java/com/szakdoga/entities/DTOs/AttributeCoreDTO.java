package com.szakdoga.entities.DTOs;

import java.util.List;

import com.szakdoga.enums.AttributeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeCoreDTO {
	private Integer id;
	private String name;
	private List<Integer> attributes;
	private AttributeType type;
}
