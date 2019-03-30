package com.szakdoga.entities.DTOs;

import lombok.Data;

@Data
public class CategoryDTO {
	public Integer id;
	public Integer parentId;
	public String name;
}
