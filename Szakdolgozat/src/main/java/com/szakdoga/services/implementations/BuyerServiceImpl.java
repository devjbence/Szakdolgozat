package com.szakdoga.services.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.DTOs.BuyerDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.ImageUploadException;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.ImageRepository;
import com.szakdoga.services.interfaces.BuyerService;
import com.szakdoga.utils.Utils;

@Service
public class BuyerServiceImpl extends BaseServiceClass<Buyer,BuyerDTO> implements BuyerService {

	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public void saveImage(Buyer entity, MultipartFile imageFile) {
		//max file méret < 64kb
		if(imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException("The imagesize is more than: "+ Utils.MAX_IMAGEFILE_SIZE/1000 + " KB");
		
		Image image = new Image();
		
		try {
			image.setFile(imageFile.getBytes());
			
			imageRepository.save(image);
			
		} catch (IOException e) {
			throw new ImageUploadException("Image could not be uploaded");
		}
		
		entity.setProfileImage(image);
		
		buyerRepository.save(entity);
	}

	@Override
	public void mapEntityToDto(Buyer entity, BuyerDTO dto) {
		dto.setId(entity.getId());
		dto.setAboutMe(entity.getAboutMe());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setProfileImage(entity.getProfileImage() == null ? 0 : entity.getProfileImage().getId());
		dto.setUsername(entity.getUser().getUsername());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(BuyerDTO dto, Buyer entity) {
		if (dto.getAboutMe() != null)
			entity.setAboutMe(dto.getAboutMe());
		if (dto.getFirstName() != null)
			entity.setFirstName(dto.getFirstName());
		if (dto.getLastName() != null)
			entity.setLastName(dto.getLastName());
	}

	@Override
	public BuyerDTO get(Integer id) {
		Buyer entity = buyerRepository.findById(id).get();

		if (entity == null)
			return null;

		BuyerDTO dto = new BuyerDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public BuyerDTO update(int id, BuyerDTO dto) {
		Buyer entity = buyerRepository.findById(id).get();

		mapDtoToEntityNonNullsOnly(dto, entity);		
		
		buyerRepository.save(entity);
		
		mapEntityToDto(entity, dto);
		
		return dto;
	}

	@Override
	public List<BuyerDTO> getAll() {
		List<BuyerDTO> dtos = new ArrayList<BuyerDTO>();

		for (Buyer entity : buyerRepository.findAll()) {
			BuyerDTO dto = new BuyerDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<BuyerDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<BuyerDTO>();

		List<BuyerDTO> dtos = getAll();
		List<BuyerDTO> pagedDtos = new ArrayList<BuyerDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<BuyerDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public int size() {
		return Math.toIntExact(buyerRepository.count());
	}
}
