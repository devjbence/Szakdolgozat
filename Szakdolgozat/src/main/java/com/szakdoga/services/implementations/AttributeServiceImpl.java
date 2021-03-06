package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.AttributeCore;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.AttributeDTO;
import com.szakdoga.exceptions.AttributeNameDoesNotExistsException;
import com.szakdoga.exceptions.DtoNullException;
import com.szakdoga.exceptions.NumericConversionException;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.exceptions.SameAttributeCoreMoreThanOnceException;
import com.szakdoga.repositories.AttributeCoreRepository;
import com.szakdoga.repositories.AttributeRepository;
import com.szakdoga.repositories.ProductRepository;
import com.szakdoga.services.interfaces.AttributeService;

@Service
public class AttributeServiceImpl extends BaseServiceClass<Attribute,AttributeDTO> implements AttributeService {

	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private AttributeCoreRepository attributeCoreRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public AttributeDTO add(AttributeDTO dto) {

		Attribute entity = new Attribute();
		AttributeCore attributeName = attributeCoreRepository.findById(dto.getAttributeCore()).get();
		Product product = productRepository.findById(dto.getProduct()).get();
		
		if(product.getAttributes().stream().anyMatch(x->x.getAttributeCore().getId().equals(attributeName.getId())))
			throw new SameAttributeCoreMoreThanOnceException("The same attribute core is given more than once");

		mapDtoToEntity(dto, entity);
		attributeRepository.save(entity);

		product.addAttribute(entity);
		productRepository.save(product);

		attributeName.addAttribute(entity);
		attributeCoreRepository.save(attributeName);
		
		mapEntityToDto(entity, dto);
		
		return dto;
	}

	@Override
	public AttributeDTO get(Integer id) {
		Attribute entity = attributeRepository.findById(id).get();

		if (entity == null)
			return null;

		AttributeDTO dto = new AttributeDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void delete(Integer id) {
		attributeRepository.deleteById(id);
	}

	@Override
	public void mapDtoToEntity(AttributeDTO dto, Attribute entity) {
		entity.setProduct(productRepository.findById(dto.getProduct()).get());
		entity.setAttributeCore(attributeCoreRepository.findById(dto.getAttributeCore()).get());
		entity.setValue(dto.getValue());
	}

	@Override
	public void mapEntityToDto(Attribute entity, AttributeDTO dto) {
		dto.setId(entity.getId());
		dto.setAttributeCore(entity.getAttributeCore().getId());
		dto.setValue(entity.getValue());
		dto.setProduct(entity.getProduct().getId());
	}

	@Override
	public AttributeDTO update(int id, AttributeDTO dto) {
		Attribute entity = attributeRepository.findById(id).get();

		mapDtoToEntityNonNullsOnly(dto, entity);

		attributeRepository.save(entity);
		
		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(AttributeDTO dto, Attribute entity) {
		if (dto == null)
			return;

		if (dto.getAttributeCore() != 0)
			entity.setAttributeCore(attributeCoreRepository.findById(dto.getAttributeCore()).get());

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

		AttributeCore attributeCore = attributeCoreRepository.findById(dto.getAttributeCore()).get();

		if (attributeCore == null) {
			throw new AttributeNameDoesNotExistsException("");
		}

		Product product = productRepository.findById(dto.getProduct()).get();

		if (product == null) {
			throw new ProductDoesNotExistsException("Product does not exists");
		}
		
		// attribútumhoz tartozó érték validálása
		switch (attributeCore.getType()) {
		case floatingpoint:

			try {
				
				Double.parseDouble(dto.getValue());
				
			} catch (Exception ex) {
				throw new NumericConversionException("Double is in wrong format at value " + dto.getValue());
			}

			break;
		case integer:

			try {

				Integer.parseInt(dto.getValue());

			} catch (Exception ex) {
				throw new NumericConversionException("Integer is in wrong format at value " + dto.getValue());
			}

			break;
		default:
			break;
		}
	}
}
