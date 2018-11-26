package com.szakdoga.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.exceptions.IdIsMissingException;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.repos.UserRepository;

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

	@Override
	public void addProduct(ProductDTO productDTO) {
		Seller seller = getSeller(productDTO.getUsername());
		Product product = new Product();
		product.setSeller(seller);
		mapProductDTOtoProduct(productDTO, product);
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
		Set<ProductCategory> categories = product.getCategories();

		// https://stackoverflow.com/a/18448699
		Iterator<ProductCategory> categoryIterator = categories.iterator();
		while (categoryIterator.hasNext()) {
			ProductCategory category = categoryIterator.next();
			categoryIterator.remove();
		}

		Seller seller = product.getSeller();
		seller.removeProduct(product);

		productRepository.delete(product);
	}

	@Override
	public void updateProduct(int productId, ProductDTO productDTO) {
		Product product = productRepository.findById(productId);
		Seller seller = product.getSeller();

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
	public void removeAllProducts(String username) {
		
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

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
}
