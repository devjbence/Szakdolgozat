package com.szakdoga.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.DTOs.ImageDTO;
import com.szakdoga.repositories.ImageRepository;
import com.szakdoga.services.interfaces.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;
	
	@Override
	public void mapDtoToEntity(ImageDTO dto, Image entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapEntityToDto(Image entity, ImageDTO dto) {
		dto.setFile(entity.getFile());
		dto.setId(entity.getId());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(ImageDTO dto, Image entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(ImageDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ImageDTO get(Integer id) {
		Image entity = imageRepository.findById(id);
		
		if(entity==null)
		{
			return null;
		}
		
		ImageDTO dto = new ImageDTO();
		
		mapEntityToDto(entity,dto);
		
		return dto;
	}

	@Override
	public void update(int id, ImageDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ImageDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImageDTO> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return Math.toIntExact(imageRepository.count());
	}
}























