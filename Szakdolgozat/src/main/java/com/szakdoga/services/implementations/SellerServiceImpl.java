package com.szakdoga.services.implementations;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.BuyerDTO;
import com.szakdoga.entities.DTOs.SellerDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.ImageUploadException;
import com.szakdoga.exceptions.NotOwnUsernameException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.services.interfaces.ProductService;
import com.szakdoga.services.interfaces.SellerService;
import com.szakdoga.utils.Utils;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private ProductCategoryRepository categoryRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ProductService productService;

	private void updateCategories(SellerDTO dto, Seller entity) {
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

	public void removeDescendants(int id) {

		Seller seller = sellerRepository.findOne(id);

		Iterator<ProductCategory> categoryIterator = seller.getCategories().iterator();
		while (categoryIterator.hasNext()) {
			ProductCategory category = categoryIterator.next();
			categoryIterator.remove();
		}

		productService.removeAllProducts(seller);
	}

	@Override
	public void saveImage(Seller entity, MultipartFile imageFile) {
		//max file méret < 64kb
		if(imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException("The imagesize is more than: "+ Utils.MAX_IMAGEFILE_SIZE/1000 + " KB");
		
		try {
			entity.setProfileImage(imageFile.getBytes());
			sellerRepository.save(entity);
		} catch (IOException e) {
			throw new ImageUploadException("Image could not be uploaded");
		}
	}

	@Override
	public byte[] getProfileImage(int id) {

		Seller entity = sellerRepository.findOne(id);
		
		if(entity == null)
			return null;
		
		byte[] profileImage = entity.getProfileImage();
		
		if (profileImage == null) {
			return Utils.getDefaultProfileImage();
		}
		return profileImage;

	}

	@Override
	public void mapDtoToEntity(SellerDTO dto, Seller entity) {
	}

	@Override
	public void mapEntityToDto(Seller entity, SellerDTO dto) {
		dto.setId(entity.getId());
		dto.setAboutMe(entity.getAboutMe());
		dto.setCategories(entity.getCategories().stream().mapToInt(b->b.getId()).boxed().collect(Collectors.toList()));
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setProfileImage(entity.getProfileImage());
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
	public void update(int id, SellerDTO dto) {
		Seller entity = sellerRepository.findOne(id);

		mapDtoToEntityNonNullsOnly(dto, entity);		
		updateCategories(dto, entity);
		
		sellerRepository.save(entity);
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

	@Override
	public void add(SellerDTO dto) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub	
	}
}
