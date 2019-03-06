package com.szakdoga.services.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.DTOs.ImageDTO;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.SellerDoesNotExistsException;
import com.szakdoga.repos.CommentRepository;
import com.szakdoga.repos.ImageRepository;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.services.interfaces.ImageService;
import com.szakdoga.services.interfaces.ProductService;
import com.szakdoga.services.interfaces.UserService;
import com.szakdoga.utils.Utils;

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
		// TODO Auto-generated method stub
		return 0;
	}
}























