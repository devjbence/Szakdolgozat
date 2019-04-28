package com.szakdoga.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.DTOs.ImageDTO;
import com.szakdoga.repositories.ImageRepository;
import com.szakdoga.services.interfaces.ImageService;
import com.szakdoga.utils.Utils;

@Service
public class ImageServiceImpl extends BaseServiceClass<Image, ImageDTO> implements ImageService{

	@Autowired
	ImageRepository imageRepository;

	@Override
	public void mapEntityToDto(Image entity, ImageDTO dto) {
		dto.setFile(entity.getFile());
		dto.setId(entity.getId());
	}
	
	@Override
	public ImageDTO get(Integer id) {
		Image entity = imageRepository.findById(id);
		ImageDTO dto = new ImageDTO();
		
		if(entity==null)
		{
			dto.setFile(Utils.getDefaultImage());
			
			return dto;
		}
		
		mapEntityToDto(entity,dto);
		
		return dto;
	}

	@Override
	public int size() {
		return Math.toIntExact(imageRepository.count());
	}
}























