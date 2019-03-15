package com.szakdoga.services.interfaces;

import java.util.List;

public interface BaseService <E,D> {
	void mapDtoToEntity( D dto,E entity);
	void mapEntityToDto(E entity,D dto);
	void mapDtoToEntityNonNullsOnly(D dto,E entity);
	
	D add(D dto);
	D get(Integer id);
	D update(int id,D dto);
	void delete(Integer id);
	void validate(D dto);
	
	List<D> getAll();
	List<D> getAll(int page,int size);
	
	int size();
}
