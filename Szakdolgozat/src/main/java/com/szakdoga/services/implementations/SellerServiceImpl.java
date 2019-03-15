package com.szakdoga.services.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.DTOs.SellerDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.ImageUploadException;
import com.szakdoga.repositories.ImageRepository;
import com.szakdoga.repositories.SellerRepository;
import com.szakdoga.services.interfaces.SellerService;
import com.szakdoga.utils.Utils;

@Service
public class SellerServiceImpl extends BaseServiceClass<Seller,SellerDTO> implements SellerService {

	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public void saveImage(Seller entity, MultipartFile imageFile) {
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
		
		sellerRepository.save(entity);
	}

	@Override
	public void mapDtoToEntity(SellerDTO dto, Seller entity) {
	}

	@Override
	public void mapEntityToDto(Seller entity, SellerDTO dto) {
		dto.setId(entity.getId());
		dto.setAboutMe(entity.getAboutMe());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setProfileImage(entity.getProfileImage() == null ? 0 : entity.getProfileImage().getId());
		dto.setUsername(entity.getUser().getUsername());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(SellerDTO dto, Seller entity) {
		if (dto.getAboutMe() != null)
			entity.setAboutMe(dto.getAboutMe());
		if (dto.getFirstName() != null)
			entity.setFirstName(dto.getFirstName());
		if (dto.getLastName() != null)
			entity.setLastName(dto.getLastName());
	}

	@Override
	public SellerDTO get(Integer id) {
		Seller entity = sellerRepository.findOne(id);

		if (entity == null)
			return null;

		SellerDTO dto = new SellerDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public SellerDTO update(int id, SellerDTO dto) {
		Seller entity = sellerRepository.findOne(id);

		mapDtoToEntityNonNullsOnly(dto, entity);		
		
		sellerRepository.save(entity);
		
		mapEntityToDto(entity, dto);
		
		return dto;
	}

	@Override
	public List<SellerDTO> getAll() {
		List<SellerDTO> dtos = new ArrayList<SellerDTO>();

		for (Seller entity : sellerRepository.findAll()) {
			SellerDTO dto = new SellerDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<SellerDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<SellerDTO>();

		List<SellerDTO> dtos = getAll();
		List<SellerDTO> pagedDtos = new ArrayList<SellerDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<SellerDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public int size() {
		return Math.toIntExact(sellerRepository.count());
	}
}
