package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.AttributeName;
import com.szakdoga.entities.DTOs.AttributeNameDTO;
import com.szakdoga.repos.AttributeNameRepository;
import com.szakdoga.repos.AttributeRepository;
import com.szakdoga.services.interfaces.AttributeNameService;

@Service
public class AttributeNameServiceImpl implements AttributeNameService{

	@Autowired
	private AttributeNameRepository attributeNameRepository;
	@Autowired
	private AttributeRepository attributeRepository;
	
	@Override
	public void mapDtoToEntity(AttributeNameDTO dto, AttributeName entity) {
		entity.setAttributes(dto.getAttributes().stream().map(x-> attributeRepository.findById(x)).collect(Collectors.toList()));
		entity.setId(dto.getId());
		entity.setName(dto.getName());
	}

	@Override
	public void mapEntityToDto(AttributeName entity, AttributeNameDTO dto) {
		dto.setName(entity.getName());
		dto.setAttributes(entity.getAttributes().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
		dto.setId(entity.getId());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(AttributeNameDTO dto, AttributeName entity) {
		if(dto.getAttributes() != null)
			entity.setAttributes(dto.getAttributes().stream().map(x-> attributeRepository.findById(x)).collect(Collectors.toList()));
		if(dto.getId() != null)
			entity.setId(dto.getId());
		if(dto.getName() != null)
			entity.setName(dto.getName());
	}

	@Override
	public void add(AttributeNameDTO dto) {
		AttributeName entity = new AttributeName();

		mapDtoToEntityNonNullsOnly(dto, entity);
		
		attributeNameRepository.save(entity);
	}

	@Override
	public AttributeNameDTO get(Integer id) {
		AttributeName entity = attributeNameRepository.findById(id);

		if (entity == null)
			return null;

		AttributeNameDTO dto = new AttributeNameDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void update(int id, AttributeNameDTO dto) {
		AttributeName entity = attributeNameRepository.findById(id);

		mapDtoToEntityNonNullsOnly(dto, entity);

		attributeNameRepository.save(entity);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AttributeNameDTO> getAll() {
		List<AttributeNameDTO> dtos = new ArrayList<AttributeNameDTO>();

		for (AttributeName entity : attributeNameRepository.findAll()) {
			AttributeNameDTO dto = new AttributeNameDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<AttributeNameDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<AttributeNameDTO>();

		List<AttributeNameDTO> dtos = getAll();
		List<AttributeNameDTO> pagedDtos = new ArrayList<AttributeNameDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<AttributeNameDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public int size() {
		return Math.toIntExact(attributeNameRepository.count());
	}

}
