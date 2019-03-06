package com.szakdoga.services.implementations;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.AttributeDTO;
import com.szakdoga.entities.DTOs.BuyerDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.ImageUploadException;
import com.szakdoga.exceptions.NotOwnUsernameException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.BuyerRepository;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.services.interfaces.BuyerService;
import com.szakdoga.utils.Utils;

@Service
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private ProductCategoryRepository categoryRepository;
	@Autowired
	private BuyerRepository buyerRepository;

	private void updateCategories(BuyerDTO dto, Buyer entity) {
		if (dto.getCategories() == null)
			return;

		for (int categoryId : dto.getCategories()) {
			if (categoryId < 0) {
				ProductCategory category = categoryRepository.findById(categoryId * (-1));
				entity.removeCategory(category);
			} else {
				ProductCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					if (!entity.getCategories().contains(category)) {
						entity.addCategory(category);
					}
				}
			}
		}
	}
	
	@Override
	public void saveImage(Buyer entity, MultipartFile imageFile) {
		//max file méret < 64kb
		if(imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException("The imagesize is more than: "+ Utils.MAX_IMAGEFILE_SIZE/1000 + " KB");
		
		try {
			entity.setProfileImage(imageFile.getBytes());
			buyerRepository.save(entity);
		} catch (IOException e) {
			throw new ImageUploadException("Image could not be uploaded");
		}
	}

	@Override
	public byte[] getProfileImage(int id) {

		Buyer entity = buyerRepository.findOne(id);
		
		if(entity == null)
			return null;
		
		byte[] profileImage = entity.getProfileImage();
		
		if (profileImage == null) {
			return Utils.getDefaultProfileImage();
		}
		return profileImage;

	}

	@Override
	public void mapDtoToEntity(BuyerDTO dto, Buyer entity) {
	}

	@Override
	public void mapEntityToDto(Buyer entity, BuyerDTO dto) {
		dto.setId(entity.getId());
		dto.setAboutMe(entity.getAboutMe());
		dto.setCategories(entity.getCategories().stream().mapToInt(b->b.getId()).boxed().collect(Collectors.toList()));
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setProfileImage(entity.getProfileImage());
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
		Buyer entity = buyerRepository.findOne(id);

		if (entity == null)
			return null;

		BuyerDTO dto = new BuyerDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void update(int id, BuyerDTO dto) {
		Buyer entity = buyerRepository.findOne(id);

		mapDtoToEntityNonNullsOnly(dto, entity);		
		updateCategories(dto, entity);
		
		buyerRepository.save(entity);
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

	@Override
	public void add(BuyerDTO dto) {
	}
	
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub	
	}
}
