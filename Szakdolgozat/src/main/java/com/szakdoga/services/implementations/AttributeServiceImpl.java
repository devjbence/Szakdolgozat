package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.AttributeName;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.AttributeDTO;
import com.szakdoga.exceptions.AttributeNameDoesNotExistsException;
import com.szakdoga.exceptions.DtoNullException;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.repos.AttributeNameRepository;
import com.szakdoga.repos.AttributeRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.services.interfaces.AttributeService;

@Service
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private AttributeNameRepository attributeNameRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void add(AttributeDTO dto) {

		Attribute entity = new Attribute();
		AttributeName attributeName = attributeNameRepository.findById(dto.getAttributeName());
		Product product = productRepository.findById(dto.getProduct());

		mapDtoToEntity(dto, entity);
		attributeRepository.save(entity);
		
		product.addAttribute(entity);
		productRepository.save(product);

		attributeName.addAttribute(entity);
		attributeNameRepository.save(attributeName);
	}

	@Override
	public AttributeDTO get(Integer id) {
		Attribute entity = attributeRepository.findById(id);

		if (entity == null)
			return null;

		AttributeDTO dto = new AttributeDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void delete(Integer id) {
		attributeRepository.delete(id);
	}

	@Override
	public void mapDtoToEntity(AttributeDTO dto, Attribute entity) {
		entity.setProduct(productRepository.findById(dto.getProduct()));
		entity.setAttributeName(attributeNameRepository.findById(dto.getAttributeName()));
		entity.setType(dto.getType());
		entity.setValue(dto.getValue());		
	}

	@Override
	public int getIntValue(String value) {
		return Integer.parseInt(value);
	}

	@Override
	public double getDoubleValue(String value) {
		return Double.parseDouble(value);
	}

	@Override
	public void mapEntityToDto(Attribute entity, AttributeDTO dto) {
		dto.setId(entity.getId());
		dto.setAttributeName(entity.getAttributeName().getId());
		dto.setType(entity.getType());
		dto.setValue(entity.getValue());
		dto.setProduct(entity.getProduct().getId());
	}

	@Override
	public void update(int id, AttributeDTO dto) {
		Attribute entity = attributeRepository.findById(id);

		mapDtoToEntityNonNullsOnly(dto, entity);

		attributeRepository.save(entity);
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(AttributeDTO dto, Attribute entity) {
		if (dto == null)
			return;

		if (dto.getAttributeName() != 0)
			entity.setAttributeName(attributeNameRepository.findById(dto.getAttributeName()));

		if (dto.getType() != null)
			entity.setType(dto.getType());

		if (dto.getValue() != null)
			entity.setValue(dto.getValue());
	}

	@Override
	public List<AttributeDTO> getAll() {
		List<AttributeDTO> dtos = new ArrayList<AttributeDTO>();

		for (Attribute entity : attributeRepository.findAll()) {
			AttributeDTO dto = new AttributeDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<AttributeDTO> getAll(int page, int size) {

		if (page < 0 || size < 0)
			return new ArrayList<AttributeDTO>();

		List<AttributeDTO> dtos = getAll();
		List<AttributeDTO> pagedDtos = new ArrayList<AttributeDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<AttributeDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;

	}

	@Override
	public int size() {
		return Math.toIntExact(attributeRepository.count());
	}

	@Override
	public void validate(AttributeDTO dto) {
		if (dto == null) {
			throw new DtoNullException("");
		}

		AttributeName attributeName = attributeNameRepository.findById(dto.getAttributeName());

		if (attributeName == null) {
			throw new AttributeNameDoesNotExistsException("");
		}
		
		Product product = productRepository.findById(dto.getProduct());
		
		if (product == null) {
			throw new ProductDoesNotExistsException("");
		}
	}
}
