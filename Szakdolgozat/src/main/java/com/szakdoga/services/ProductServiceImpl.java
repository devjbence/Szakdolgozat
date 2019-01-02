package com.szakdoga.services;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.ProductComment;
import com.szakdoga.entities.ProductImage;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.ProductCommentDTO;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.exceptions.IdIsMissingException;
import com.szakdoga.exceptions.ImageDoesNotExistsException;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.NotOwnUsernameException;
import com.szakdoga.exceptions.ProductCommentDoesNotExistsException;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.exceptions.ProductToUserDoesNotExistsException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.ProductCommentRepository;
import com.szakdoga.repos.ProductImageRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.repos.UserRepository;
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
	private ProductCommentRepository productCommentRepository;
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
		
		deleteAllCommentsByProductId(productId);
		
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

	@Override
	public void createComment(ProductCommentDTO commentDTO) {
		if (commentDTO == null)
			return;

		Product product = productRepository.findById(commentDTO.getProductId());

		if (product == null)
			throw new ProductDoesNotExistsException("The product does not exists !");

		User user = userRepository.findByUsername(getCurrentUsername());

		if (product.getSeller().getUser().getId() != user.getId())
			throw new ProductToUserDoesNotExistsException("The given product by the given user does not exists");

		ProductComment comment = new ProductComment();

		comment.setBuyer(user.getBuyer());
		comment.setMessage(commentDTO.getMessage());
		comment.setProduct(product);

		productCommentRepository.save(comment);
	}

	@Override
	public void updateComment(ProductCommentDTO commentDTO) {
		if (commentDTO == null)
			return;

		ProductComment comment = productCommentRepository.findById(commentDTO.getProductCommentId());

		if (comment == null)
			throw new ProductCommentDoesNotExistsException("The comment does not exists !");
		
		if(!comment.getBuyer().getUser().getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");

		comment.setMessage(commentDTO.getMessage());

		productCommentRepository.save(comment);
	}

	@Override
	public void deleteComment(Integer productCommentId) {
		if (productCommentId == null)
			throw new ProductCommentDoesNotExistsException("The comment does not exists !");

		ProductComment comment = productCommentRepository.findById(productCommentId);

		if (comment == null)
			throw new ProductCommentDoesNotExistsException("The comment does not exists !");
		
		if(!comment.getBuyer().getUser().getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");

		comment.getProduct().removeComment(comment);
		comment.getBuyer().removeComment(comment);

		productCommentRepository.save(comment);

		productCommentRepository.delete(comment);
	}
	
	@Override
	public void deleteAllComments(String username) {
		
		if(!username.equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");
		
		List<Integer> commentIds = productCommentRepository.getCommentsByUsername(username).stream()
				.mapToInt(c -> c.getId()).boxed().collect(Collectors.toList());

		for (int commentId : commentIds)
			deleteComment(commentId);
	}
	
	public void deleteAllCommentsByProductId(Integer productId) {
		
		Product product = productRepository.findById(productId);
		if (product == null)
			throw new ProductDoesNotExistsException("The product does not exists !");
		
		if(!product.getSeller().getUser().getUsername().equals(getCurrentUsername()))
			throw new NotOwnUsernameException("The given username is not your own!");
		
		List<Integer> commentIds = productCommentRepository.getCommentsByProductId(productId).stream()
				.mapToInt(c -> c.getId()).boxed().collect(Collectors.toList());

		for (int commentId : commentIds)
			deleteComment(commentId);
	}

	private void mapCommentToCommentDTO(ProductComment comment, ProductCommentDTO commentDTO) {
		commentDTO.setMessage(comment.getMessage());
		commentDTO.setProductCommentId(comment.getId());
		commentDTO.setProductId(comment.getProduct().getId());
		commentDTO.setUsername(comment.getBuyer().getUser().getUsername());
	}

	private List<ProductCommentDTO> getCommentsByProductId(Integer productId) {
		Product product = productRepository.findById(productId);
		if (product == null)
			throw new ProductDoesNotExistsException("The product does not exists !");
		List<ProductComment> comments = product.getComments();
		List<ProductCommentDTO> commentDTOs = new ArrayList<ProductCommentDTO>();

		for (ProductComment comment : comments) {
			ProductCommentDTO commentDTO = new ProductCommentDTO();
			mapCommentToCommentDTO(comment, commentDTO);
			commentDTOs.add(commentDTO);
		}

		return commentDTOs;
	}

	@Override
	public List<ProductCommentDTO> getCommentsByProductId(Integer productId, Integer page,Integer size) {
		if (page < 0 || size < 0)
			return new ArrayList<ProductCommentDTO>();

		List<ProductCommentDTO> dtos = getCommentsByProductId(productId);
		List<ProductCommentDTO> pagedDtos = new ArrayList<ProductCommentDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<ProductCommentDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public ProductCommentDTO getComment(Integer commentId) {
		ProductComment comment = productCommentRepository.findById(commentId);
		
		if (comment == null)
			throw new ProductCommentDoesNotExistsException("The comment does not exists !");
		
		ProductCommentDTO commentDTO = new ProductCommentDTO();

		mapCommentToCommentDTO(comment, commentDTO);

		return commentDTO;
	}

}
