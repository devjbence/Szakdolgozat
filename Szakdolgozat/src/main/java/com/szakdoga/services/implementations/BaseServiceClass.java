package com.szakdoga.services.implementations;

import java.util.List;

import com.szakdoga.services.interfaces.BaseService;

public abstract class BaseServiceClass<E, D> implements BaseService<E, D> {

	@Override
	public void mapDtoToEntity(D dto, E entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapEntityToDto(E entity, D dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(D dto, E entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public D add(D dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public D get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public D update(int id, D dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<D> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<D> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void validate(D dto)
	{
		
	}
	
}
