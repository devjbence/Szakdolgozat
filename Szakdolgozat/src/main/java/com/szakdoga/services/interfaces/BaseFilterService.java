package com.szakdoga.services.interfaces;

import java.util.List;

public interface BaseFilterService<DTO,Entity,Filter> {
	List<DTO> getAll(Filter filter);
	List<DTO> getAll(Filter filter,int page, int size);
	int size(Filter filter);
	void mapEntitesToDtos(List<Entity> entites,List<DTO> dtos);
	void validate(Filter filter);
}
