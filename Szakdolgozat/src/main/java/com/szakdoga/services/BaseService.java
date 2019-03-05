package com.szakdoga.services;

import java.util.List;

public interface BaseService <E,D> {
	void mapDtoToEntity( D dto,E entity);
	void mapEntityToDto(E entity,D dto);
	void mapDtoToEntityNonNullsOnly(D dto,E entity);
	
	void add(D dto);
	D get(Integer id);
	void update(int id,D dto);
	void delete(Integer id);
	
	List<D> getAll();
	List<D> getAll(int page,int size);
	
	int size();
}
