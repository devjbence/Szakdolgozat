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
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.SellerDoesNotExistsException;
import com.szakdoga.repos.CommentRepository;
import com.szakdoga.repos.ImageRepository;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.services.interfaces.ProductService;
import com.szakdoga.services.interfaces.UserService;
import com.szakdoga.utils.Utils;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductCategoryRepository categoryRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private CommentRepository commentRepository;

	private void updateCategories(ProductDTO dto, Product entity) {
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
	public void addImage(Integer productId, MultipartFile imageFile) {
		Product product = productRepository.findById(productId);

		// max file méret < 64kb
		if (imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException(
					"The imagesize is more than: " + Utils.MAX_IMAGEFILE_SIZE / 1000 + " KB");

		Image image = new Image();		

		try {
			image.setFile(imageFile.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		imageRepository.save(image);

		product.addImage(image);
		
		productRepository.save(product);
	}

	@Override
	public void mapDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setSeller(sellerRepository.findById(dto.getSellerId()));
	}

	@Override
	public void mapEntityToDto(Product entity, ProductDTO dto) {
		dto.setCategories(entity.getCategories().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
		dto.setComments(entity.getComments().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
		dto.setImages(entity.getImages().stream().mapToInt(x->x.getId()).boxed().collect(Collectors.toList()));
		dto.setSellerId(entity.getSeller().getId());
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setName(entity.getName());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(ProductDTO dto, Product entity) {
		entity.setComments(dto.getComments().stream().map(c->commentRepository.findById(c)).collect(Collectors.toList()));
		entity.setImages(dto.getImages().stream().map(c->imageRepository.findById(c)).collect(Collectors.toSet()));
		entity.setName(dto.getName());
		entity.setSeller(sellerRepository.findById(dto.getSellerId()));
	}

	@Override
	public void add(ProductDTO dto) {
		Product entity = new Product();
		Seller seller = userService.getCurrentUser().getSeller();

		mapDtoToEntity(dto, entity);

		entity.setSeller(seller);

		productRepository.save(entity);
		
		seller.addProduct(entity);
		
		sellerRepository.save(seller);
	}

	@Override
	public ProductDTO get(Integer id) {
		Product entity = productRepository.findById(id);

		if (entity == null)
			return null;

		ProductDTO dto = new ProductDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void update(int id, ProductDTO dto) {
		Product entity = productRepository.findById(id);

		mapDtoToEntityNonNullsOnly(dto, entity);
		updateCategories(dto,entity);

		productRepository.save(entity);
	}

	@Override
	public void delete(Integer id) {
		//removeAllImages(id);
		
		Product product = productRepository.findById(id);
		Set<ProductCategory> categories = product.getCategories();

		Iterator<ProductCategory> categoryIterator = categories.iterator();
		while (categoryIterator.hasNext()) {
			categoryIterator.remove();
		}

		Seller seller = product.getSeller();
		seller.removeProduct(product);

		productRepository.delete(product);
	}

	@Override
	public List<ProductDTO> getAll() {
		List<ProductDTO> dtos = new ArrayList<ProductDTO>();

		for (Product entity : productRepository.findAll()) {
			ProductDTO dto = new ProductDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<ProductDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<ProductDTO>();

		List<ProductDTO> dtos = getAll();
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
	public int size() {
		return Math.toIntExact(productRepository.count());
	}

	@Override
	public void ValidateDto(ProductDTO dto) {
		Seller seller = sellerRepository.findById(dto.getSellerId());
		
		if(seller == null)
		{
			throw new SellerDoesNotExistsException("Seller with id="+dto.getSellerId()+" does not exists");
		}
	}
}























