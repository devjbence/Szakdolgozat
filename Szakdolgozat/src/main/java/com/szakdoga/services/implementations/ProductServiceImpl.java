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
import com.szakdoga.entities.Bid;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.Category;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.DTOs.CategoryDTO;
import com.szakdoga.entities.DTOs.CategoryDTOMin;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.enums.ProductType;
import com.szakdoga.exceptions.AlreadySoldException;
import com.szakdoga.exceptions.CouldNotUploadImageException;
import com.szakdoga.exceptions.FixedPriceProductDoesNotHavePriceException;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.NotBiddingProductException;
import com.szakdoga.exceptions.NotFixedPricedProductException;
import com.szakdoga.exceptions.OverdueException;
import com.szakdoga.exceptions.ProductDateNullException;
import com.szakdoga.exceptions.SmallerPriceException;
import com.szakdoga.repositories.CommentRepository;
import com.szakdoga.repositories.ImageRepository;
import com.szakdoga.repositories.BidRepository;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.CategoryRepository;
import com.szakdoga.repositories.ProductRepository;
import com.szakdoga.repositories.SellerRepository;
import com.szakdoga.services.interfaces.ProductService;
import com.szakdoga.services.interfaces.UserService;
import com.szakdoga.utils.EmailUtil;
import com.szakdoga.utils.Utils;

@Service
public class ProductServiceImpl extends BaseServiceClass<Product, ProductDTO> implements ProductService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private BidRepository bidRepository;
	@Autowired
	private EmailUtil emailUtil;

	private void updateCategories(ProductDTO dto, Product entity) {
		if (dto.getCategories() == null)
			return;

		for (int categoryId : dto.getCategories()) {
			if (categoryId < 0) {
				Category category = categoryRepository.findById(categoryId * (-1));
				entity.removeCategory(category);
			} else {
				Category category = categoryRepository.findById(categoryId);
				if (category != null) {
					if(entity.getCategories() == null)
					{
						entity.addCategory(category);
					}
					else if (!entity.getCategories().contains(category)) {
						entity.addCategory(category);
					}
				}
			}
		}
	}

	@Override
	public int addImage(Integer productId, MultipartFile imageFile) {
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

		int id = imageRepository.save(image).getId();

		product.addImage(image);

		productRepository.save(product);
		
		return id;
	}

	@Override
	public void mapDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setSeller(sellerRepository.findById(dto.getSeller()));
		entity.setEnd(dto.getEnd());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setType(dto.getType());
	}

	@Override
	public void mapEntityToDto(Product entity, ProductDTO dto) {
		dto.setCategories(entity.getCategories() == null ? new ArrayList<Integer>()
				: entity.getCategories().stream().mapToInt(x -> x.getId()).boxed().collect(Collectors.toList()));
		dto.setComments(entity.getComments() == null ? new ArrayList<Integer>()
				: entity.getComments().stream().mapToInt(x -> x.getId()).boxed().collect(Collectors.toList()));
		dto.setImages(entity.getImages() == null ? new ArrayList<Integer>()
				: entity.getImages().stream().mapToInt(x -> x.getId()).boxed().collect(Collectors.toList()));
		dto.setAttributes(entity.getAttributes() == null ? new ArrayList<Integer>()
				: entity.getAttributes().stream().mapToInt(x -> x.getId()).boxed().collect(Collectors.toList()));
		dto.setBiddings(entity.getBiddings() == null ? new ArrayList<Integer>()
				: entity.getBiddings().stream().mapToInt(x -> x.getId()).boxed().collect(Collectors.toList()));
		dto.setSeller(entity.getSeller().getId());
		dto.setBuyer(entity.getBuyer() == null ? 0 : entity.getBuyer().getId());
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEnd(entity.getEnd());
		dto.setPrice(entity.getPrice());
		dto.setType(entity.getType());
		dto.setActive(entity.getActive());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(ProductDTO dto, Product entity) {
		if(dto.getComments() != null)
			entity.setComments(dto.getComments().stream().map(c -> commentRepository.findById(c)).collect(Collectors.toList()));
		if(dto.getImages() != null)
			entity.setImages(dto.getImages().stream().map(c -> imageRepository.findById(c)).collect(Collectors.toSet()));
		if(dto.getName() != null)
			entity.setName(dto.getName());
		if(dto.getSeller() != null)
			entity.setSeller(sellerRepository.findById(dto.getSeller()));
		if(dto.getEnd() != null)
			entity.setEnd(dto.getEnd());
		if(dto.getDescription() != null)
			entity.setDescription(dto.getDescription());
		if(dto.getPrice() != null)
			entity.setPrice(dto.getPrice());
		if(dto.getType() != null)
			entity.setType(dto.getType());
	}

	@Override
	public ProductDTO add(ProductDTO dto) {
		Product entity = new Product();
		Seller seller = userService.getCurrentUser().getSeller();
		
		mapDtoToEntity(dto, entity);
		updateCategories(dto,entity);

		entity.setSeller(seller);
		entity.setActive(true);

		productRepository.save(entity);

		seller.addProduct(entity);

		sellerRepository.save(seller);

		mapEntityToDto(entity, dto);

		return dto;
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
	public ProductDTO update(int id, ProductDTO dto) {
		Product entity = productRepository.findById(id);

		mapDtoToEntityNonNullsOnly(dto, entity);
		updateCategories(dto, entity);

		productRepository.save(entity);

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void delete(Integer id) {
		// removeAllImages(id);

		Product product = productRepository.findById(id);
		Set<Category> categories = product.getCategories();

		Iterator<Category> categoryIterator = categories.iterator();
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
	public void saveImage(Integer id, MultipartFile file) {
		Product entity = productRepository.findById(id);
		Image image = new Image();

		try {
			image.setFile(file.getBytes());
		} catch (IOException e) {
			throw new CouldNotUploadImageException("File could not be uploaded");
		}
		imageRepository.save(image);

		entity.addImage(image);
		productRepository.save(entity);
	}

	@Override
	public void removeImage(Integer entityId, Integer imageId) {
		Image image = imageRepository.findById(imageId);
		Product entity = productRepository.findById(entityId);

		entity.removeImage(image);
		imageRepository.delete(image);

		productRepository.save(entity);
	}

	@Override
	public void validate(ProductDTO dto) {
		if (dto.getType() == ProductType.FixedPrice && dto.getPrice() == null)
			throw new FixedPriceProductDoesNotHavePriceException("The fixed priced product should have a price");

		if (dto.getEnd() == null)
			throw new ProductDateNullException("The end date is required");
	}

	@Override
	public void buy(Integer id) {
		Product entity = productRepository.findById(id);
		Buyer buyer = userService.getCurrentUser().getBuyer();

		if (entity.getType() != ProductType.FixedPrice)
			throw new NotFixedPricedProductException("The product is not fix priced");

		if (entity.getBuyer() != null)
			throw new AlreadySoldException("The product is already sold");

		if (!entity.getActive())
			throw new OverdueException("The selling period has ended");

		entity.setBuyer(buyer);
		entity.setActive(false);
		productRepository.save(entity);

		buyer.addProduct(entity);

		buyerRepository.save(buyer);
		
		emailUtil.sendSimpleMessage(buyer.getUser().getEmail(), "You baught a product", "You baught the product: "+ entity.getName());
	}

	@Override
	public void bid(int entityId, int price) {
		Product entity = productRepository.findById(entityId);
		Buyer buyer = userService.getCurrentUser().getBuyer();

		if (!entity.getActive()) {
			throw new OverdueException("The selling period has ended");
		}

		if (entity.getType() != ProductType.Bidding) {
			throw new NotBiddingProductException("The product is not for bidding");
		}

		Bid bid = new Bid();
		bid.setBuyer(buyer);
		bid.setPrice(price);
		bid.setProduct(entity);

		if (entity.getBiddings() != null && !entity.getBiddings().isEmpty()
				&& price <= entity.getBiddings().stream().mapToInt(x -> x.getPrice()).max().getAsInt()) {
			throw new SmallerPriceException("The price must be higher");
		}

		bidRepository.save(bid);

		entity.addBid(bid);
		productRepository.save(entity);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		
		List<CategoryDTO> cdtos = new ArrayList<CategoryDTO>();
		
		for( Category cat : categoryRepository.findAll() ) 
		{
			CategoryDTO cdto = new CategoryDTO();
			cdto.setId(cat.getId());
			cdto.setName(cat.getName());
			cdto.setParentId(cat.getParentId());
			cdtos.add(cdto);
		}
		
		return cdtos;
	}
}
