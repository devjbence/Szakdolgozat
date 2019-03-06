package com.szakdoga.services.implementations;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.ProductImage;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.exceptions.IdIsMissingException;
import com.szakdoga.exceptions.ImageDoesNotExistsException;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.NotOwnUsernameException;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.ProductImageRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.services.interfaces.ProductService;
import com.szakdoga.services.interfaces.UserService;
import com.szakdoga.utils.Utils;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductCategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductImageRepository productImageRepository;
	@Autowired
	private HttpServletRequest request;

	private String getCurrentUsername()
	{
	    Principal principal = request.getUserPrincipal();

	    return principal.getName();
	}

	@Override
	public void addProduct(ProductDTO productDTO) {
		Seller seller = getSeller(getCurrentUsername());
		
		Product product = new Product();
		product.setSeller(seller);
		mapProductDTOtoProduct(productDTO, product);

		/*
		 * TODO: a product attributja kapja meg, hogy fixáras vagy licites, csak egyik
		 * lehet true, másik legyen false Ha a dto-ban mindkettő true /false akkor
		 * exception
		 */

		seller.addProduct(product);
		sellerRepository.save(seller);
	}

	@Override
	public void removeProduct(Integer productId) {
		if (productId == null)
			throw new IdIsMissingException("Job ID is missing for delete!");

		Product product = productRepository.findById(productId);
		if (product == null)
			throw new ProductDoesNotExistsException("Product does not exists!");
		
		//TODO: delete all comments
		
		removeAllImages(productId);
		
		Set<ProductCategory> categories = product.getCategories();

		// https://stackoverflow.com/a/18448699
		Iterator<ProductCategory> categoryIterator = categories.iterator();
		while (categoryIterator.hasNext()) {
			ProductCategory category = categoryIterator.next();
			categoryIterator.remove();
		}

		removeAllImages(productId);

		Seller seller = product.getSeller();
		seller.removeProduct(product);

		productRepository.delete(product);
	}

	@Override
	public void updateProduct(int productId, ProductDTO productDTO) {
		Product product = productRepository.findById(productId);
		Seller seller = product.getSeller();
		
		if(!seller.getUser().getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");

		mapProductDTOtoProduct(productDTO, product);
		sellerRepository.save(seller);
	}

	@Override
	public void removeAllProducts(Seller seller) {
		for (int productId : productRepository.findAll().stream().filter(j -> j.getSeller().equals(seller))
				.mapToInt(j -> j.getId()).toArray()) {
			removeProduct(productId);
		}
	}

	@Override
	public void removeAllProducts() {

		User user = userRepository.findByUsername(getCurrentUsername());

		Seller seller = sellerRepository.findByUser(user);

		removeAllProducts(seller);
	}

	private Seller getSeller(String username) {
		User user = userService.checkUserValues(username);
		Seller seller = sellerRepository.findByUser(user);
		return seller;
	}

	private void mapProductDTOtoProduct(ProductDTO productDTO, Product product) {

		if (productDTO.getName() != null)
			product.setName(productDTO.getName());
		if (productDTO.getDescription() != null)
			product.setDescription(productDTO.getDescription());

		mapCategoriesToProduct(productDTO.getCategories(), product);
	}

	private void mapCategoriesToProduct(List<Integer> categories, Product product) {
		if (categories == null || categories.isEmpty())
			return;
		for (int categoryId : categories) {
			if (categoryId < 0) {
				ProductCategory category = categoryRepository.findById(categoryId * (-1));
				if (category != null)
					product.removeCategories(category);
			} else {
				ProductCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					product.addCategories(category);
				}

			}
		}
	}

	@Override
	public void addImage(Integer productId, MultipartFile imageFile) {
		Product product = productRepository.findById(productId);
		if (product == null)
			throw new ProductDoesNotExistsException("The product does not exists !");

		User user = product.getSeller().getUser();
		
		if(!user.getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");
		
		userService.checkIfActivated(user);

		// max file méret < 64kb
		if (imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException(
					"The imagesize is more than: " + Utils.MAX_IMAGEFILE_SIZE / 1000 + " KB");

		ProductImage image = new ProductImage();
		image.setProduct(product);

		try {
			image.setProductImage(imageFile.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		product.addImage(image);
		productRepository.save(product);
	}

	@Override
	public void removeImage(Integer imageId) {
		ProductImage productImage = productImageRepository.findById(imageId);
		if (productImage == null)
			throw new ImageDoesNotExistsException("The image does not exists!");

		Product product = productImage.getProduct();
		
		if(!product.getSeller().getUser().getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");
		
		product.removeImage(productImage);

		productRepository.save(product);
		productImageRepository.delete(imageId);
	}

	@Override
	public void removeAllImages(Integer productId) {
		Product product = productRepository.findById(productId);
		if (product == null)
			throw new ProductDoesNotExistsException("The product does not exists !");
		
		if(!product.getSeller().getUser().getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");

		List<Integer> imageIds = product.getImages().stream().mapToInt(im -> im.getId()).boxed()
				.collect(Collectors.toList());

		for (int imageId : imageIds) {
			removeImage(imageId);
		}
	}

	@Override
	public List<Integer> getAllProductImageIds(Integer productId) {
		Product product = productRepository.findById(productId);
		if (product == null)
			throw new ProductDoesNotExistsException("The product does not exists !");

		List<Integer> imageIds = product.getImages().stream().mapToInt(im -> im.getId()).boxed()
				.collect(Collectors.toList());
		return imageIds;
	}

	@Override
	public byte[] getProductImage(Integer imageId) {

		ProductImage productImage = productImageRepository.findById(imageId);
		if (productImage == null)
			throw new ImageDoesNotExistsException("The image does not exists!");

		byte[] image = productImage.getProductImage();

		if (image == null)
			throw new ImageDoesNotExistsException("The image does not exists!");

		return image;
	}
}
