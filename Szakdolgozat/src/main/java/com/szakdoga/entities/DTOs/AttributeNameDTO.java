package com.szakdoga.entities.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeNameDTO {
	private Integer id;
	private String name;
	private List<Integer> attributes;
}
