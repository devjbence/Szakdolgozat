package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.AttributeOperation;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.entities.DTOs.ProductFilterCore;
import com.szakdoga.entities.DTOs.ProductFilterDTO;
import com.szakdoga.exceptions.AttributeNameDoesNotExistsException;
import com.szakdoga.exceptions.CategoryDoesNotExistsException;
import com.szakdoga.repos.AttributeNameRepository;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.services.interfaces.ProductFilterService;

@Service
public class ProductFilterServiceImpl implements ProductFilterService{
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AttributeNameRepository attributeNameRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	private void addEntityIfNotAlreadyContained(List<Product> entites,Product entity)
	{
		if(!entites.stream().anyMatch(x->x.getId() == entity.getId()))
		{
			entites.add(entity);
		}
	}
	
	private static boolean compareNumericValues(double attriValue,double filterValue,AttributeOperation operation)
	{	
		switch(operation)
		{
			case equal:
				
				if(filterValue == attriValue)
					return true;
				
				break;
				
			case greaterThanOrEqual:
				if(filterValue >= attriValue)
					return true;
				
				break;
				
			case lessThanOrEqual:
				if(filterValue <= attriValue)
					return true;
				
				break;
				
			case greaterThen:
				if(filterValue > attriValue)
					return true;
				
				break;
				
			case lessThan:
				if(filterValue < attriValue)
					return true;	
				
				break;
		}
		
		return false;
	}
	
	private boolean compareNumericValues(int attriValue,int filterValue,AttributeOperation operation)
	{	
		switch(operation)
		{
			case equal:
				
				if(filterValue == attriValue)
					return true;				
				break;
				
			case greaterThanOrEqual:
				if(filterValue >= attriValue)
					return true;	
				break;
				
			case lessThanOrEqual:
				if(filterValue <= attriValue)
					return true;	
				break;
				
			case greaterThen:
				if(filterValue > attriValue)
					return true;	
				break;
				
			case lessThan:
				if(filterValue < attriValue)
					return true;	
				break;
		}
		
		return false;
	}

	@Override
	public List<ProductDTO> getAll(ProductFilterDTO filter) {
		
		List<ProductDTO> dtos = new ArrayList<ProductDTO>();
		List<Product> entites = productRepository.findAll();
		
		//kategóriákra szűrés
		entites=entites.stream()
		.filter(x->x.getCategories()!=null && x.getCategories()
				.stream()
				.anyMatch(c->filter.getCategories() != null && filter.getCategories().contains(c.getId())))
		.collect(Collectors.toList());
		
		List<Product> filteredEntites = new ArrayList<Product>();
		
		//szűrés attrikra
		for(ProductFilterCore filterCore: filter.getProductFilterCores())
		{					
			for(Product entity : entites) {
				List<Attribute> attributes = entity.getAttributes();
				
				for(Attribute attribute : attributes)
				{
					if(attribute.getId() == filterCore.getAttributeName())
					{
						AttributeOperation operation = AttributeOperation.values()[filterCore.getAttributeOperation()];

						switch(attribute.getType())
						{
							case characters:
								
								if(attribute.getValue().equals(filterCore.getValue()))
								{
									addEntityIfNotAlreadyContained(filteredEntites,entity);	
								}
								
								break;
								
							case floatingpoint:
								
								if(compareNumericValues(attribute.getDoubleValue(),filterCore.getDoubleValue(),operation))
								{
									addEntityIfNotAlreadyContained(filteredEntites,entity);	
								}
								
								break;
							case integer:

								if(compareNumericValues(attribute.getIntValue(),filterCore.getIntValue(),operation))
								{
									addEntityIfNotAlreadyContained(filteredEntites,entity);	
								}
								
								break;
						}		
					}
				}
			}
		}
		
		mapEntitesToDtos(entites,dtos);
		
		return dtos;
	}
	
	@Override
	public void mapEntitesToDtos(List<Product> entites, List<ProductDTO> dtos) {
		for(Product entity : entites)
		{
			ProductDTO dto = new ProductDTO();
			
			dto.setAttributes(entity.getAttributes().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
			dto.setCategories(entity.getCategories().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
			dto.setComments(entity.getComments().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
			dto.setImages(entity.getImages().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
			dto.setDescription(entity.getDescription());
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setSeller(entity.getSeller().getId());
			dto.setUsername(entity.getSeller().getUser().getUsername());
			
			dtos.add(dto);
		}
	}

	@Override
	public List<ProductDTO> getAll(ProductFilterDTO filter, int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<ProductDTO>();

		List<ProductDTO> dtos = getAll(filter);
		List<ProductDTO> pagedDtos = new ArrayList<ProductDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<ProductDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public int size(ProductFilterDTO filter) {
		return getAll(filter).size();
	}

	@Override
	public void validate(ProductFilterDTO filter) {
		for(int catId : filter.getCategories())
		{
			if(productCategoryRepository.findById(catId) == null)
			{
				throw new CategoryDoesNotExistsException("Category doesn't exists");
			}
		}
		
		for(ProductFilterCore filterCore : filter.getProductFilterCores())
		{
			if(attributeNameRepository.findById(filterCore.getAttributeName()) == null)
			{
				throw new AttributeNameDoesNotExistsException("Attributename does not exists");
			}
		}
	}
}









