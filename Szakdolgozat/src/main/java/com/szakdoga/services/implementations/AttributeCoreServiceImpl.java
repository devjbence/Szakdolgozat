package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.AttributeCore;
import com.szakdoga.entities.DTOs.AttributeCoreDTO;
import com.szakdoga.repos.AttributeCoreRepository;
import com.szakdoga.repos.AttributeRepository;
import com.szakdoga.services.interfaces.AttributeCoreService;

@Service
public class AttributeCoreServiceImpl implements AttributeCoreService{

	@Autowired
	private AttributeCoreRepository attributeCoreRepository;
	@Autowired
	private AttributeRepository attributeRepository;
	
	@Override
	public void mapDtoToEntity(AttributeCoreDTO dto, AttributeCore entity) {
		entity.setAttributes(dto.getAttributes().stream().map(x-> attributeRepository.findById(x)).collect(Collectors.toList()));
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setType(dto.getType());
	}

	@Override
	public void mapEntityToDto(AttributeCore entity, AttributeCoreDTO dto) {
		dto.setName(entity.getName());
		dto.setAttributes(entity.getAttributes().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
		dto.setId(entity.getId());
		dto.setType(entity.getType());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(AttributeCoreDTO dto, AttributeCore entity) {
		if(dto.getAttributes() != null)
			entity.setAttributes(dto.getAttributes().stream().map(x-> attributeRepository.findById(x)).collect(Collectors.toList()));
		if(dto.getId() != null)
			entity.setId(dto.getId());
		if(dto.getName() != null)
			entity.setName(dto.getName());
		if(dto.getType() != null)
			entity.setType(dto.getType());
	}

	@Override
	public void add(AttributeCoreDTO dto) {
		AttributeCore entity = new AttributeCore();

		mapDtoToEntityNonNullsOnly(dto, entity);
		
		attributeCoreRepository.save(entity);
	}

	@Override
	public AttributeCoreDTO get(Integer id) {
		AttributeCore entity = attributeCoreRepository.findById(id);

		if (entity == null)
			return null;

		AttributeCoreDTO dto = new AttributeCoreDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void update(int id, AttributeCoreDTO dto) {
		AttributeCore entity = attributeCoreRepository.findById(id);

		mapDtoToEntityNonNullsOnly(dto, entity);

		attributeCoreRepository.save(entity);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AttributeCoreDTO> getAll() {
		List<AttributeCoreDTO> dtos = new ArrayList<AttributeCoreDTO>();

		for (AttributeCore entity : attributeCoreRepository.findAll()) {
			AttributeCoreDTO dto = new AttributeCoreDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<AttributeCoreDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<AttributeCoreDTO>();

		List<AttributeCoreDTO> dtos = getAll();
		List<AttributeCoreDTO> pagedDtos = new ArrayList<AttributeCoreDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<AttributeCoreDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public int size() {
		return Math.toIntExact(attributeCoreRepository.count());
	}

}
